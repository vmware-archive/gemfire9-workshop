package io.pivotal.app.function;

import io.pivotal.function.SizeFunction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;

public class ExecuteSizeFunction
{
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private ClientCache cache = null;

    public ExecuteSizeFunction()
    {
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "config/query-client.xml");
        cache = ccf.create();
    }

	public void run(String regionName)
    {
        Execution execution = FunctionService.onServer(this.cache).withArgs("/" + regionName);

        ResultCollector<?, ?> collector = execution.execute(SizeFunction.ID);

        System.out.println (
                String.format("Region %s contains %s entries",
                        "/" + regionName, ((List<?>) collector.getResult()).get(0)));
    }

    public static void main(String[] args)
    {
        ExecuteSizeFunction test = new ExecuteSizeFunction();
        test.run("employees");
        test.run("departments");
    }
}
