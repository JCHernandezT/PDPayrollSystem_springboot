package co.com.juan.poly.data.dao;

import co.com.juan.poly.data.model.UserEntity;

public interface UserRepo extends BaseRepo<UserEntity, Long> {

	public UserEntity findByUsername(String username);
	
}
