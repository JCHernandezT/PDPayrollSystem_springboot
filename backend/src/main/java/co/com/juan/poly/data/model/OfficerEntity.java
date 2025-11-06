package co.com.juan.poly.data.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
