package co.com.juan.poly.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RAN")
public class RankEntity extends FeatureEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
/*	@OneToMany(mappedBy = "rank", cascade = { CascadeType.REMOVE })
	private List<OfficerEntity> officers;*/
	
	public RankEntity() {
	
	}
	
	public RankEntity(long id, String name, BigDecimal appraisal) {
		super(id, name, appraisal);
	}
	
/*	public RankEntity(long id, String name, BigDecimal appraisal, List<OfficerEntity> officers) {
		super(id, name, appraisal);
		this.officers = officers;
	}*/
	
/*	public List<OfficerEntity> getOfficers() {
		return officers;
	}
	
	public void setOfficers(List<OfficerEntity> officers) {
		this.officers = officers;
	}*/
	
}
