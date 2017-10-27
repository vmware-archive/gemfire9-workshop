package io.pivotal.app.repos;

import io.pivotal.domain.Department;

import java.util.Collection;

import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Region("departments")
public interface DeptRepository extends CrudRepository<Department, String> 
{
	@Query("SELECT * FROM /departments")
	Collection<Department> myFindAll();
}
