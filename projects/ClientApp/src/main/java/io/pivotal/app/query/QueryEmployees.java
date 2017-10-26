package io.pivotal.app.query;

import io.pivotal.domain.Employee;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;

public class QueryEmployees
{
    private ClientCache cache = null;
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public QueryEmployees()
    {
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "config/query-client.xml");
        cache = ccf.create();
    }

    public void run() throws Exception
    {
        QueryService queryService = cache.getQueryService();

        Query query = queryService.newQuery("SELECT * FROM /employees where deptno = 10");
        logger.log (Level.INFO, "\nExecuting query:\n\t" + query.getQueryString());

        Object result = query.execute();

        Collection<?> collection = ((SelectResults<?>)result).asList();
        logger.log (Level.INFO, String.format("%s Employees in department 10", collection.size()));

        Iterator<?> iter = collection.iterator();

        System.out.println ("*************************************************************");
       while (iter.hasNext())
        {
            Employee emp = (Employee) iter.next();
            System.out.println(emp.toString());
        }
       System.out.println ("*************************************************************");
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        QueryEmployees test = new QueryEmployees();

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
