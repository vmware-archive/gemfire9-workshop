/*
 * The MultiuserSecurityExample QuickStart Example.
 *
 * This example takes the following steps:
 *
 * 1. Create a GemFire Cache with multiuser enabled.
 * 2. Creates userCache using user "root". Who is authorized to do get and put operations.
 * 3. Creates userCache using user "writer2". Who is authorized to do only put operation. It tries to do get operation and gets NotAuthorizedException.
 * 4.  Close the Cache.
 *
 */

// Use standard namespaces
using System;

// Use the GemFire namespace
using GemStone.GemFire.Cache.Generic;
using System.Collections.Generic;
using System.Collections;
namespace GemStone.GemFire.Cache.Generic.QuickStart
{
  // The MultiuserSecurityExample QuickStart example.
  class MultiuserSecurityExample
  {
    private static string getFuncIName = "MultiGetFunctionI";

    public void RunMultiuserSecurityExample()
    {
      // Create client's Authentication Intializer and Credentials using api ( Same can be set to gfcpp.properties & comment following code ).
      Properties<string, string> secProp = Properties<string, string>.Create<string, string>();

      //By setting this property client will send credential in encrypted form.
      //to do this one need to setup OpenSSL.
      //secProp.Insert("security-client-dhalgo", "Blowfish:128");

      // Connect to the GemFire Distributed System using the settings from the gfcpp.properties file by default.
      // Create a GemFire Cache.
      CacheFactory cacheFactory = CacheFactory.CreateCacheFactory(null)
          .AddServer("192.168.1.121", 40404);

      Cache cache = cacheFactory.SetMultiuserAuthentication(true).Create();

      Console.WriteLine("Created the GemFire Cache");

      RegionFactory regionFactory = cache.CreateRegionFactory(RegionShortcut.PROXY);

      IRegion<string, string> region = regionFactory.Create<string, string>("exampleRegion");

      Console.WriteLine("Created the Region Programmatically.");

      runWithUserRoot(cache);
      runWithUserWriter(cache);
      runWithUserReader(cache);

      Console.In.ReadLine();

      cache.Close();

      Console.WriteLine("Client disconnected from the GemFire Distributed System");
    }
    
    void runWithUserRoot(Cache cache)
    {
      Console.WriteLine("------------------------------------------------------------");
      Console.WriteLine("Logging in as root.  Should have access to reads and writes ");
      Console.WriteLine("------------------------------------------------------------");

      //user "root" 's credential
      Properties<string, object> credentials = Properties<string, object>.Create<string, object>();
      //user = "root" has permission to do put/get both  
      credentials.Insert("security-username", "root");
      credentials.Insert("security-password", "root");

      // Create user cache by passing credentials
      IRegionService userCache1 = cache.CreateAuthenticatedView(credentials);

      Console.WriteLine("Created the Region Programmatically. 2");
      // Create region using usercache
      IRegion<string, string> userRegion1 = userCache1.GetRegion<string, string>("exampleRegion");

      //doing operation on behalf of user "root"
      userRegion1["key-1"] = "val-1";

      string result = userRegion1["key-1"];

      // commented 1/2015 for Morgan Stanley since they do not use functions
      //to execute function on server
        /*
      Execution<object> exc = FunctionService<object>.OnServer(userCache1);

      bool getResult = true;
      ArrayList args1 = new ArrayList();
      args1.Add("key-1");

      try
      {
          IResultCollector<object> rc = exc.WithArgs<ArrayList>(args1).Execute(
          getFuncIName, getResult);
          ICollection<object> executeFunctionResult = rc.GetResult();
          Console.WriteLine("on one server: result count= {0}.", executeFunctionResult.Count);

          List<object> resultList1 = new List<object>();
          foreach (List<object> item in executeFunctionResult)
          {
              foreach (object subitem in item)
              {
                  resultList1.Add(subitem);
              }
          }

          for (int i = 0; i < resultList1.Count; i++)
          {
              Console.WriteLine("on one server:get:result[{0}]={1}.", i, (string)resultList1[i]);
          }
      }
      catch (Exception e)
      {
          Console.WriteLine(e);
      }
         * 
         * */


      //to execute Query

      // Get the QueryService from the Cache.
      QueryService<string, string> qrySvc = userCache1.GetQueryService<string, string>();

      Console.WriteLine("Got the QueryService from the user Cache");

      // Execute a Query which returns a ResultSet.    
      Query<string> qry = qrySvc.NewQuery("SELECT DISTINCT * FROM /exampleRegion");
      ISelectResults<string> results = qry.Execute();

      Console.WriteLine("ResultSet Query returned {0} rows", results.Size);

      userCache1.Close();

      Console.WriteLine("User root done put/get ops successfully");
    }

    void runWithUserWriter(Cache cache)
    {
        Console.WriteLine();
        Console.WriteLine("------------------------------------------------------------");
        Console.WriteLine("Logging in as writer0.  Has access to writes but not reads. ");
        Console.WriteLine("------------------------------------------------------------");
        //user "writer2" 's credential
        Properties<string, object> credentials = Properties<string, object>.Create<string, object>();
        //user = "writer2" has permission to do put ops only  
        credentials.Insert("security-username", "writer0");
        credentials.Insert("security-password", "writer0");

        // Create user cache by passing credentials
        IRegionService userCache2 = cache.CreateAuthenticatedView(credentials);

        // Create region using usercache
        IRegion<string, string> userRegion2 = userCache2.GetRegion<string, string>("exampleRegion");

        bool exceptiongot = false;


        userRegion2["key-1"] = "key-1";
        Console.WriteLine("put successful");

        try
        {
            //"writer0" trying to do get operation on region, it should get NotAuthorized exception
            Console.WriteLine();
            Console.WriteLine("attempting get. Should fail.");
            string res = userRegion2["key-1"];
        }
        catch (NotAuthorizedException ex)
        {
            Console.WriteLine("Got expected UnAuthorizedException: {0}", ex.InnerException);
            exceptiongot = true;
        }

        try
        {
            //"writer0" trying to do put operation on "key2", which is forbidden for writer0
            Console.WriteLine();
            Console.WriteLine("attempting put of key2. Should fail because key2 is forbidden");
            string res = userRegion2["key2"];
        }
        catch (NotAuthorizedException ex)
        {
            Console.WriteLine("Writer0 forbidden to write key2. Got expected UnAuthorizedException: {0}", ex.Message);
            exceptiongot = true;
        }


        if (exceptiongot == false)
        {
            Console.WriteLine("Example FAILED: Did not get expected NotAuthorizedException");
        }
        else
        {
            Console.WriteLine("User writer0 got expected exception while doing get operation.");
        }

        //close the user cache
        userCache2.Close();
    }

    void runWithUserReader(Cache cache)
    {
        Console.WriteLine();
        Console.WriteLine("------------------------------------------------------------");
        Console.WriteLine("Logging in as reader1.  Has access to reads but not writes. ");
        Console.WriteLine("------------------------------------------------------------");

        //user "reader1" 's credential
        Properties<string, object> credentials = Properties<string, object>.Create<string, object>();
        //user = "reader1" has permission to do get ops only  
        credentials.Insert("security-username", "reader1");
        credentials.Insert("security-password", "reader1");

        // Create user cache by passing credentials
        IRegionService userCache2 = cache.CreateAuthenticatedView(credentials);

        // Create region using usercache
        IRegion<string, string> userRegion2 = userCache2.GetRegion<string, string>("exampleRegion");

        bool exceptiongot = false;


        string res = userRegion2["key-1"];
        Console.WriteLine("get successful");

        try
        {
            //"reader1" trying to do put operation on region, it should get NotAuthorized exception
            Console.WriteLine();
            Console.WriteLine("attempting put. Should fail.");
            userRegion2["key-1"] = "key-1";
        }
        catch (NotAuthorizedException ex)
        {
            Console.WriteLine("Got expected UnAuthorizedException: {0}", ex.Message);
            exceptiongot = true;
        }


        if (exceptiongot == false)
        {
            Console.WriteLine("Example FAILED: Did not get expected NotAuthorizedException");
        }
        else
        {
            Console.WriteLine("User reader1 got expected exception while doing put operation.");
        }

        //close the user cache
        userCache2.Close();
    }
    
    static void Main(string[] args)
    {
      try
      {
        MultiuserSecurityExample ex = new MultiuserSecurityExample();

        //Run Security Client
        ex.RunMultiuserSecurityExample();

      }
      // An exception should not occur
      catch (GemFireException gfex)
      {
        Console.WriteLine("FAILED: MultiuserSecurityExample GemFire Exception: {0}", gfex.Message);
      }
    }
  }
}
