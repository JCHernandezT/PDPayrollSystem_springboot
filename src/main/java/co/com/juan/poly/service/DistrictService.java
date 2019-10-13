package co.com.juan.poly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import co.com.juan.poly.data.dao.DistrictRepo;
import co.com.juan.poly.data.model.DistrictEntity;

/**
 * Class used for operations related with DistrictEntity manipulation.
 * 
 * @author Juan Hernández
 */
@Service
public class DistrictService {

	@Autowired
	private DistrictRepo districtRepo;
	
	public List<DistrictEntity> findAll() {
		return (List<DistrictEntity>) districtRepo.findAll();
	}
	
	public DistrictEntity findOne(Long id) {
		return districtRepo.findOne(id);
	}

	public DistrictEntity saveOne(DistrictEntity districtEntity) throws DataIntegrityViolationException {
		return districtRepo.save(districtEntity);
	}
	
	public void deleteOne(long id) {
		districtRepo.delete(id);;
	}
	
	public boolean exist(Long id) {
		return districtRepo.exists(id);
	}

}
