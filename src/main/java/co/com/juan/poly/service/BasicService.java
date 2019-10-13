package co.com.juan.poly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import co.com.juan.poly.data.dao.BasicSalaryRepo;
import co.com.juan.poly.data.model.BasicSalary;

@Service
public class BasicService {
	
	@Autowired
	private BasicSalaryRepo basicSalaryRepo;
	
	public BasicSalary findBasicSalary() {
		return basicSalaryRepo.findFirst();
	}
	
	public boolean alreadyExist() {
		long id = 0L;
		if (basicSalaryRepo.findFirst() != null) {
			id = basicSalaryRepo.findFirst().getId();
		}
		return basicSalaryRepo.exists(id);
	}
	
	public BasicSalary saveOne(BasicSalary basicSalary) throws DataIntegrityViolationException {
		return basicSalaryRepo.save(basicSalary);
	}

}
