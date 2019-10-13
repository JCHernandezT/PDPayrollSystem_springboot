package co.com.juan.poly.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DIS")
public class DistrictEntity extends FeatureEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*@OneToMany(mappedBy = "district", cascade = { CascadeType.REMOVE })
	private List<OperativeOfficerEntity> operativeOfficers;*/
	
	public DistrictEntity() {}
	
/*	public DistrictEntity(long id, String name, BigDecimal appraisal, List<OperativeOfficerEntity> operativeOfficers) {
		super(id, name, appraisal);
		this.operativeOfficers = operativeOfficers;
	}*/
	
	public DistrictEntity(long id, String name, BigDecimal appraisal) {
		super(id, name, appraisal);
	}
	
/*	public List<OperativeOfficerEntity> getOperativeOfficers() {
		return operativeOfficers;
	}
	
	public void setOperativeOfficers(List<OperativeOfficerEntity> operativeOfficers) {
		this.operativeOfficers = operativeOfficers;
	}*/
	
}
