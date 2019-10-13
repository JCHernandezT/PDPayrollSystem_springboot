package co.com.juan.poly.data.dao;

import org.springframework.data.jpa.repository.Query;

import co.com.juan.poly.data.model.BasicSalary;

public interface BasicSalaryRepo extends BaseRepo<BasicSalary, Long> {
	
	@Query(value = "SELECT * FROM basicsalary ORDER BY id ASC LIMIT 1", nativeQuery = true)
	BasicSalary findFirst();
}
