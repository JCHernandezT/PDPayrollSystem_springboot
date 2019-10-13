package co.com.juan.poly.data.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "FEATURE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class FeatureEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false, length = 50)
	private long id;

	@Column(length = 50, unique = true, nullable = false)
	@NotEmpty
	@Size(max = 50)
	private String name;

	@Column(nullable = false, precision = 3, scale = 2)
	@NotNull
	@Range(min = 0, max = 1)
	private BigDecimal appraisal;
	
	public FeatureEntity() {
		
	}
	
	public FeatureEntity(long id, String name, BigDecimal appraisal) {
		this.id = id;
		this.name = name;
		this.appraisal = appraisal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(BigDecimal appraisal) {
		this.appraisal = appraisal;
	}
	
}
