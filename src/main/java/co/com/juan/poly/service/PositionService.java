package co.com.juan.poly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import co.com.juan.poly.data.dao.PositionRepo;
import co.com.juan.poly.data.model.PositionEntity;

@Service
public class PositionService {
	
	@Autowired
	private PositionRepo positionRepo;
	
	public List<PositionEntity> findAll() {
		return (List<PositionEntity>) positionRepo.findAll();
	}
	
	public PositionEntity findOne(Long id) {
		return positionRepo.findOne(id);
	}

	public PositionEntity saveOne(PositionEntity positionEntity) throws DataIntegrityViolationException {
		return positionRepo.save(positionEntity);
	}
	
	public void deleteOne(long id) {
		positionRepo.delete(id);;
	}
	
	public boolean exist(Long id) {
		return positionRepo.exists(id);
	}
}

