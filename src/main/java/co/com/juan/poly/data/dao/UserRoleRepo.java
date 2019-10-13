package co.com.juan.poly.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import co.com.juan.poly.data.model.UserRoleEntity;

public interface UserRoleRepo extends BaseRepo<UserRoleEntity, Long> {
	
	@Query("select a.role from UserRoleEntity a, UserEntity b where b.username=?1 and a.userid=b.id")
	public List<String> findRoleByUserName(String username);
	
}
