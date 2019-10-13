package co.com.juan.poly.data.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("ADM")
public class AdministrativeOfficerEntity extends OfficerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "position")
	@NotNull
	private PositionEntity position;

	public AdministrativeOfficerEntity() {
	}

	public AdministrativeOfficerEntity(long id, String dni, String name, String lastName, RankEntity rank,
			PositionEntity position) {
		this.setId(id);
		this.setDni(dni);
		this.setName(name);
		this.setLastName(lastName);
		this.setRank(rank);
		this.position = position;
	}

	public PositionEntity getPosition() {
		return position;
	}

	public void setPosition(PositionEntity position) {
		this.position = position;
	}

}
