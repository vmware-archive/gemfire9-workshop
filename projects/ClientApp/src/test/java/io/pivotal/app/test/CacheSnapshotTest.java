package io.pivotal.app.test;

import io.pivotal.app.repos.DeptRepository;
import io.pivotal.app.repos.EmpRepository;
import io.pivotal.domain.Department;
import io.pivotal.domain.Employee;

import java.util.Collection;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.snapshot.CacheSnapshotService;
import com.gemstone.gemfire.internal.cache.GemFireCacheImpl;
import com.gemstone.gemfire.internal.cache.snapshot.CacheSnapshotServiceImpl;

public class CacheSnapshotTest 
{

	private Cache cache = null;
	
	public CacheSnapshotTest() 
	{
        CacheFactory cf = new CacheFactory();
        cf.set("cache-xml-file", "config/peer-cache.xml");
        cf.set("locators", "gemhost[10334]");
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

