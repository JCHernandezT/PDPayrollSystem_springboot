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
		return administrativeRepo.save(administrativeEntity);
	}
	
	/**
	 * Saves a list of administratives in the database.
	 * 
	 * @param administrativeEntities
	 *            the entities to be saved
	 * @return the saved entities
	 */
	public List<AdministrativeOfficerEntity> saveList(Iterable<AdministrativeOfficerEntity> administrativeEntities) {
		return administrativeRepo.saveAll(administrativeEntities);
	}
	
	/**
	 * Finds out an administrative in the database.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return the found entity
	 */
	public AdministrativeOfficerEntity findOne(Long id) {
		return administrativeRepo.findById(id).orElse(null);
	}
	
	/**
	 * Finds out all the administratives in the database.
	 * 
	 * @return the list of entities
	 */
	public List<AdministrativeOfficerEntity> findAll() {
		return (List<AdministrativeOfficerEntity>) administrativeRepo.findAll();
	}
	
	/**
	 * Checks if an administrative exist in the database.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return true if entity exist
	 */
	public boolean exist(Long id) {
		return administrativeRepo.existsById(id);
	}
	
	/**
	 * Counts the amount of administratives in the database.
	 * 
	 * @return count if entity exist
	 */
	public long count() {
		return administrativeRepo.count();
	}
	
	/**
	 * Deletes and administrative from the database using its id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public void deleteById(Long id) {
		administrativeRepo.deleteById(id);
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
		administrativeRepo.deleteAll(administrativeEntities);
	}
	
	/**
	 * Deletes all the administratives from the database.
	 */
	public void deleteAll() {
		administrativeRepo.deleteAll();
	}
	
	// ------------------------------------- ADDITIONAL OPERATIONS
	
	public void deleteOne(long id) {
		administrativeRepo.deleteById(id);
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
