package io.pivotal.app.repos;

import io.pivotal.domain.Department;

import java.util.Collection;

import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DeptRepository extends CrudRepository<Department, String> 
{
	Department findByName(String name);
	
	@Query("SELECT * FROM /departments")
	Collection<Department> myFindAll();
}
