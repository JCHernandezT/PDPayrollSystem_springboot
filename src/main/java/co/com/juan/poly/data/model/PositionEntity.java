package co.com.juan.poly.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("POS")
public class PositionEntity extends FeatureEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "position", cascade = { CascadeType.REMOVE })
	private List<AdministrativeOfficerEntity> administrativeOfficers;
	
	public PositionEntity() {
	
	}
	
	public PositionEntity(long id, String name, BigDecimal appraisal,
			List<AdministrativeOfficerEntity> administrativeOfficers) {
		super(id, name, appraisal);
		this.administrativeOfficers = administrativeOfficers;
	}
	
	public List<AdministrativeOfficerEntity> getAdministrativeOfficers() {
		return administrativeOfficers;
	}
	
	public void setAdministrativeOfficers(List<AdministrativeOfficerEntity> administrativeOfficers) {
		this.administrativeOfficers = administrativeOfficers;
	}
	
}
