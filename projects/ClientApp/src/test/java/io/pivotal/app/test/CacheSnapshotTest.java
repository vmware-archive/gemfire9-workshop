package io.pivotal.app.test;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.snapshot.CacheSnapshotService;
import org.apache.geode.internal.cache.GemFireCacheImpl;
import org.apache.geode.internal.cache.snapshot.CacheSnapshotServiceImpl;

public class CacheSnapshotTest 
{

	private Cache cache = null;
	
	public CacheSnapshotTest() 
	{
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "config/peer-cache.xml");
        cf.set("locators", "localhost[10334]");
        cache = cf.create();
	}

	public void run()
	{
		CacheSnapshotService cacheSnapshotService = new CacheSnapshotServiceImpl((GemFireCacheImpl) cache);
			
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		CacheSnapshotTest test = new CacheSnapshotTest();
		System.out.println("\nStarting CacheSnapshotTest Tests.... ");
		test.run();
		System.out.println("\nAll done.... ");
		
	}
}

