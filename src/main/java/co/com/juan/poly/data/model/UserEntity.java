package co.com.juan.poly.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "\"user\"")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(length = 20, unique = true, nullable = false)
	@NotEmpty
	@Size(max = 30)
	private String username;

	@Column(length = 60, unique = true, nullable = false)
	@NotEmpty
	private String password;
	
	@Column(length = 60, unique = true, nullable = false)
	private String email;
	
	private int enabled;
	
	public UserEntity() {
		
	}

	public UserEntity(UserEntity userEntity) {
		this.id = userEntity.id;
		this.username = userEntity.username;
		this.email = userEntity.email;
		this.password = userEntity.password;
		this.enabled = userEntity.enabled;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getEnabled() {
		return enabled;
	}


	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

}
