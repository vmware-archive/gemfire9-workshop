package io.pivotal.app.query;

import io.pivotal.domain.Employee;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

public class QueryEmployeeByKey
{
    private ClientCache cache = null;
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public QueryEmployeeByKey()
    {
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "config/query-client.xml");
        cache = ccf.create();
    }

    public void run() throws Exception
    {
        Region<Integer,Employee> employees = cache.getRegion("employees");

        String empKey = "7370";
        Employee emp = employees.get(empKey);

        logger.log (Level.INFO, "*************************************************************");
        logger.log (Level.INFO, emp.toString());
        logger.log (Level.INFO, "*************************************************************");
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        QueryEmployeeByKey test = new QueryEmployeeByKey();

        try
        {
            test.run();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
