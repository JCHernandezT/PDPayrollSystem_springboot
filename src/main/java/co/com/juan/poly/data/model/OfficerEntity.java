package co.com.juan.poly.data.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Inheritance
@DiscriminatorColumn(name = "OFFICER_TYPE")
@Table(name = "OFFICER")
public abstract class OfficerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private long id;

	@Column(length = 10, unique = true, nullable = false)
	@NotEmpty
	@Size(max = 10)
	private String dni;

	@Column(length = 50, nullable = false)
	@NotEmpty
	@Size(max = 50)
	private String name;

	@Column(length = 50, nullable = false)
	@NotEmpty
	@Size(max = 50)
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "rank")
	@NotNull
	private RankEntity rank;
	
	@Range(min = 0, max = 1000000)
	private BigDecimal salary;

	public OfficerEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public RankEntity getRank() {
		return rank;
	}

	public void setRank(RankEntity rank) {
		this.rank = rank;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
}
