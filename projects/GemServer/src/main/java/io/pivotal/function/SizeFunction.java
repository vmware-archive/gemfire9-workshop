package io.pivotal.function;

import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;

public class SizeFunction implements Function
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
        String[] arguments = (String[]) context.getArguments();
        String regionName = (String) arguments[0];
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
