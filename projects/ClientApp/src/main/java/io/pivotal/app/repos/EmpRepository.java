package io.pivotal.app.repos;

import io.pivotal.domain.Employee;

import java.util.Collection;

import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmpRepository extends CrudRepository<Employee, String>
{
	@Query("SELECT * FROM /employees where deptno = $1")
	Collection<Employee> empsInDeptno(int deptno);
	
}
