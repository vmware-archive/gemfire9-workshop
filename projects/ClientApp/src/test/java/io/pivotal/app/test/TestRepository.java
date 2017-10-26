package io.pivotal.app.test;

import io.pivotal.app.repos.DeptRepository;
import io.pivotal.app.repos.EmpRepository;
import io.pivotal.domain.Department;
import io.pivotal.domain.Employee;

import java.util.Collection;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRepository 
{

	private ConfigurableApplicationContext ctx = null;
	
	public TestRepository() 
	{
		ctx = new ClassPathXmlApplicationContext("config/application-context.xml");
	}

	public void run()
	{
		DeptRepository deptRepos = (DeptRepository) ctx.getBean(DeptRepository.class);
		
		// get quick size
		System.out.println("\n** Size of dept repository **");
		System.out.println("Size = " + deptRepos.count());	

		// call findOne CRUD method by key
		System.out.println("\n** calling  deptRepos.findOne(\"20\") **");
		Department dept = (Department) deptRepos.findOne("20");
		System.out.println(dept);
		
		
		Collection<Department> deps = (Collection<Department>) deptRepos.findAll(); 
		
		System.out.println("\n** All Departments using -> deptRepos.findAll()");
		System.out.println("Collection<Department> findAll(); ** ");
		
		for (Department d: deps)
		{
			System.out.println(d.toString());
		}
		
		EmpRepository empRepos = (EmpRepository) ctx.getBean(EmpRepository.class);

		// get quick size
		System.out.println("\n** Size of emp repository **");
		System.out.println("Size = " + empRepos.count());
		
		Collection<Employee> emps = empRepos.empsInDeptno(40);
		System.out.println("\n ** All Employees in dept 40 using -> Collection<Employee> empsInDeptno(int deptno) **");
		for (Employee e: emps)
		{
			System.out.println(e.toString());
		}
		
		
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		TestRepository test = new TestRepository();
		System.out.println("\nStarting Spring Data GemFire Repository Tests.... ");
		test.run();
		System.out.println("\nAll done.... ");
		
	}
}

