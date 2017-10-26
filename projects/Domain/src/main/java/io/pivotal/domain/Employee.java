package io.pivotal.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

@SuppressWarnings("serial")
@Region("employees")
public class Employee implements Serializable
{	
	@Id
	private int empno;
	private String name;
	private String job;
	private int deptno;
	
	public Employee() 
	{
	}

	public Employee(int empno, String name, String job, int deptno) {
		super();
		this.empno = empno;
		this.name = name;
		this.job = job;
		this.deptno = deptno;
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	   
    public void replaceLastName(String lastName) {
    	name = name.substring(0, name.indexOf(" ")) + lastName;
    }

	@Override
	public String toString() {
		return "Employee [empno=" + empno + ", name=" + name + ", job=" + job
				+ ", deptno=" + deptno + "]";
	}

	public String toCSVFormat() {
		return empno + "," + name + "," + job + "," + deptno;
	}
}
