package io.pivotal.function;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;

public class SizeFunction extends FunctionAdapter
{
    private static final long serialVersionUID = 1L;

    public static final String ID = "size-function";

    private GemFireCache cache;

    public SizeFunction()
    {
        this.cache = CacheFactory.getAnyInstance();
    }

    @Override
    public void execute(FunctionContext context)
    {
        String regionName = (String) context.getArguments();
        Region<Object, Object> region = this.cache.getRegion(regionName);
        System.out.println("Getting size of region " + region.getFullPath());
        context.getResultSender().lastResult(region.size());
    }

    @Override
    public String getId()
    {
        return ID;
    }

}
