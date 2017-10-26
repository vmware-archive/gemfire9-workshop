package io.pivotal.app.test;

import io.pivotal.domain.Employee;

import java.util.Collection;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.GemfireTemplate;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.SelectResults;

public class TestGemFireTemplate 
{

	private ConfigurableApplicationContext ctx = null;
	
	public void init() 
	{
		ctx = new ClassPathXmlApplicationContext("config/application-context.xml");
	}

	public void run ()
	{
		@SuppressWarnings("unchecked")
		GemfireTemplate empTemplate = new GemfireTemplate((Region<Integer, Employee>) ctx.getBean("employees"));
		
		System.out.println("-> template.query() test \n ");
		
		SelectResults<?> results = empTemplate.query("deptno = 40");
		@SuppressWarnings("unchecked")
		Collection<Employee> emps = (Collection<Employee>) results.asList();
		
		
		for (Employee e: emps)
		{
			System.out.println(e.toString());
		}
		
		System.out.println("\n-> template.get(key) test \n ");
		
		Employee emp = empTemplate.get("7373");
		
		System.out.println(emp.toString());
		
		System.out.println("\n-> template.find() test \n ");
		
		SelectResults<Employee> clerkEmpResults = 
				empTemplate.find("SELECT * from /employees WHERE job=$1", "CLERK");
		
		Collection<Employee> clerkEmps = (Collection<Employee>) clerkEmpResults.asList();
		
		for (Employee e: clerkEmps)
		{
			System.out.println(e.toString());
		}
		
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		TestGemFireTemplate test = new TestGemFireTemplate();
		System.out.println("\nStarting Spring Data GemFire Template Test.... \n");
		test.init();
		test.run();
		System.out.println("\nAll done.... ");
		
	}
}
