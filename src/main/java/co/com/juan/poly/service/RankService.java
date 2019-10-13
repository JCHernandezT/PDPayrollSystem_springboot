package co.com.juan.poly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import co.com.juan.poly.data.dao.RankRepo;
import co.com.juan.poly.data.model.RankEntity;

@Service
public class RankService {
	
	@Autowired
	private RankRepo rankRepo;
	
	public List<RankEntity> findAll() {
		return (List<RankEntity>) rankRepo.findAll();
	}
	
	public RankEntity findOne(Long id) {
		return rankRepo.findOne(id);
	}

	public RankEntity saveOne(RankEntity rankEntity) throws DataIntegrityViolationException {
		return rankRepo.save(rankEntity);
	}
	
	public void deleteOne(long id) {
		rankRepo.delete(id);
	}
	
	public boolean exist(Long id) {
		return rankRepo.exists(id);
	}
}
