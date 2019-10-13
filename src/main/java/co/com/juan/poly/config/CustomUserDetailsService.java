package co.com.juan.poly.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.com.juan.poly.data.dao.UserRepo;
import co.com.juan.poly.data.dao.UserRoleRepo;
import co.com.juan.poly.data.model.UserEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserRoleRepo userRoleRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepo.findByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("No user present: " + username);
		} else {
			List<String> userRoles = userRoleRepo.findRoleByUserName(username);
			return new CustomUserDetails(userEntity, userRoles);
		}
	}

}
