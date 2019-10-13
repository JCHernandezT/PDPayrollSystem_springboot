package co.com.juan.poly.data.dao;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Generic Base Interface for CRUD operations with database.
 * 
 * @author Juan Camilo Hernández.
 * @see CrudRepository.
 */
@NoRepositoryBean
public interface BaseRepo<T, ID extends Serializable> extends CrudRepository<T, ID> {

}
