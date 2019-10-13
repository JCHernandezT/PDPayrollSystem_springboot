package co.com.juan.poly.data.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("OPT")
public class OperativeOfficerEntity extends OfficerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "district")
	@NotNull
	private DistrictEntity district;
	
	public OperativeOfficerEntity() {}
	
	public OperativeOfficerEntity(long id, String dni, String name, String lastName, RankEntity rank,
			DistrictEntity district) {
		this.setId(id);
		this.setDni(dni);
		this.setName(name);
		this.setLastName(lastName);
		this.setRank(rank);
		this.district = district;
	}
	
	public DistrictEntity getDistrict() {
		return district;
	}
	
	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}
	
}
