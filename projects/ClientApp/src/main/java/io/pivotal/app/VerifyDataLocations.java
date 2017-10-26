package io.pivotal.app;

import io.pivotal.domain.Employee;

import java.util.Map;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper;
import com.gemstone.gemfire.distributed.DistributedMember;

public class VerifyDataLocations
{
    private Cache cache = null;

    public VerifyDataLocations()
    {
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "config/datalocations-cache-no-storage.xml");
        cf.set("locators", "gemhost[10334]");
        cache = cf.create();
    }

    public void run() throws InterruptedException
    {
        Region<String,Employee> exampleRegion = cache.getRegion("employees");
        System.out.println("Employees region size = " + exampleRegion.size());
       
        /*
         * Java 1.7 style
         */
        
        System.out.println ("*************************************************************");
        for (Map.Entry<String, Employee> entry : exampleRegion.entrySet()) {
            DistributedMember member =
                    PartitionRegionHelper.getPrimaryMemberForKey(exampleRegion, (String) entry.getKey());
            System.out.println
                    (String.format("\"Primary Member [Host=%s, Id=%s - Key=%s, Value=%s]",
                            member.getHost(), member.getId(), entry.getKey(), entry.getValue()));
        }
        
        /*
         * Java 1.8 style
         */
//        exampleRegion.entrySet()
//        	.forEach((Map.Entry<String, Employee> entry) -> {
//            DistributedMember member =
//                    PartitionRegionHelper.getPrimaryMemberForKey(exampleRegion, (String) entry.getKey());
//            System.out.println
//                    (String.format("\"Primary Member [Host=%s, Id=%s - Key=%s, Value=%s]",
//                            member.getHost(), member.getId(), entry.getKey(), entry.getValue()));
//        });

        System.out.println ("*************************************************************");

        cache.close();

    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        // TODO Auto-generated method stub
        VerifyDataLocations test = new VerifyDataLocations();
        test.run();
        System.exit(1);
    }
}
