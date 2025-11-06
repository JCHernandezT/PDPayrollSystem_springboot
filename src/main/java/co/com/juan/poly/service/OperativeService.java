package co.com.juan.poly.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.juan.poly.data.dao.OperativeRepo;
import co.com.juan.poly.data.model.OperativeOfficerEntity;

@Service
public class OperativeService {
	
	@Autowired
	private OperativeRepo operativeRepo;
	
	@Autowired
	private BasicService basicService;
	
	public OperativeOfficerEntity saveOne(OperativeOfficerEntity operativeEntity) {
		return operativeRepo.save(operativeEntity);
	}
	
	public OperativeOfficerEntity findOne(Long id) {
		return operativeRepo.findById(id).orElse(null);
	}
	
	public List<OperativeOfficerEntity> findAll() {
		return (List<OperativeOfficerEntity>) operativeRepo.findAll();
	}
	
	public void deleteOne(long id) {
		operativeRepo.deleteById(id);
	}
	
	public boolean exist(Long id) {
		return operativeRepo.existsById(id);
	}
	
	public BigDecimal salaryCalculation(OperativeOfficerEntity operativeEntity) {
		return (operativeEntity.getRank().getAppraisal().add(operativeEntity.getDistrict().getAppraisal()))
				.multiply(basicService.findBasicSalary().getAmount());
	}
	
	public void recalculateSalaries() {
		List<OperativeOfficerEntity> list = (List<OperativeOfficerEntity>) operativeRepo.findAll();
		for (OperativeOfficerEntity e : list) {
			e.setSalary(this.salaryCalculation(e));
			this.saveOne(e);
		}
	}
	
	public BigDecimal calculateOperativePayroll() {
		BigDecimal x = new BigDecimal("0.0");
		for (OperativeOfficerEntity e : operativeRepo.findAll()) {
			x = x.add(e.getSalary());
		}
		return x;
	}
	
}
