package co.com.juan.poly.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.juan.poly.data.dao.AdministrativeRepo;
import co.com.juan.poly.data.model.AdministrativeOfficerEntity;

/**
 * Class used for operations related with AdministrativeEntity manipulation.
 * 
 * @author Juan Hernández
 */
@Service
public class AdministrativeService {
	
	@Autowired
	private AdministrativeRepo administrativeRepo;
	
	@Autowired
	private BasicService basicService;
	
	// ---------------------------------------- STANDARD OPERATIONS
	
	/**
	 * Saves an administrative in the database.
	 * 
	 * @param administrativeEntity
	 *            the entity to be saved
	 * @return the saved entity
	 */
	public AdministrativeOfficerEntity saveOne(AdministrativeOfficerEntity administrativeEntity) {
		AdministrativeOfficerEntity entity = administrativeRepo.save(administrativeEntity);
		return entity;
	}
	
	/**
	 * Saves a list of administratives in the database.
	 * 
	 * @param administrativeEntities
	 *            the entities to be saved
	 * @return the saved entities
	 */
	public List<AdministrativeOfficerEntity> saveList(Iterable<AdministrativeOfficerEntity> administrativeEntities) {
		List<AdministrativeOfficerEntity> list = (List<AdministrativeOfficerEntity>) administrativeRepo
				.save(administrativeEntities);
		return list;
	}
	
	/**
	 * Finds out an administrative in the database.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return the found entity
	 */
	public AdministrativeOfficerEntity findOne(Long id) {
		AdministrativeOfficerEntity entity = administrativeRepo.findOne(id);
		return entity;
	}
	
	/**
	 * Finds out all the administratives in the database.
	 * 
	 * @return the list of entities
	 */
	public List<AdministrativeOfficerEntity> findAll() {
		List<AdministrativeOfficerEntity> list = (List<AdministrativeOfficerEntity>) administrativeRepo.findAll();
		return list;
	}
	
	/**
	 * Checks if an administrative exist in the database.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return true if entity exist
	 */
	public boolean exist(Long id) {
		boolean flag = administrativeRepo.exists(id);
		return flag;
	}
	
	/**
	 * Counts the amount of administratives in the database.
	 * 
	 * @return true if entity exist
	 */
	public long count() {
		long count = administrativeRepo.count();
		return count;
	}
	
	/**
	 * Deletes and administrative from the database using its id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public void deleteById(Long id) {
		administrativeRepo.delete(id);
	}
	
	/**
	 * Deletes and administrative from the database using the entity.
	 * 
	 * @param administrativeEntity
	 *            the entity to be deleted.
	 */
	public void deleteAnEntity(AdministrativeOfficerEntity administrativeEntity) {
		administrativeRepo.delete(administrativeEntity);
	}
	
	/**
	 * Deletes a list administratives from the database.
	 * 
	 * @param administrativeEntities
	 *            the list of entities to be deleted.
	 */
	public void deleteListOfEntities(Iterable<AdministrativeOfficerEntity> administrativeEntities) {
		administrativeRepo.delete(administrativeEntities);
	}
	
	/**
	 * Deletes all the administratives from the database.
	 */
	public void deleteAll() {
		administrativeRepo.deleteAll();
	}
	
	// ------------------------------------- ADDITIONAL OPERATIONS
	
	public void deleteOne(long id) {
		administrativeRepo.delete(id);
	}
	
	public BigDecimal salaryCalculation(AdministrativeOfficerEntity administrativeEntity) {
		return (administrativeEntity.getRank().getAppraisal().add(administrativeEntity.getPosition().getAppraisal()))
				.multiply(basicService.findBasicSalary().getAmount());
	}
	
	public void recalculateSalaries() {
		List<AdministrativeOfficerEntity> list = (List<AdministrativeOfficerEntity>) administrativeRepo.findAll();
		for (AdministrativeOfficerEntity e : list) {
			e.setSalary(this.salaryCalculation(e));
			this.saveOne(e);
		}
	}
	
	public BigDecimal calculateAdministrativePayroll() {
		BigDecimal x = new BigDecimal("0.0");
		for (AdministrativeOfficerEntity e : administrativeRepo.findAll()) {
			x = x.add(e.getSalary());
		}
		return x;
	}
	
}
