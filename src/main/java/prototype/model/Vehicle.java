package prototype.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

import prototype.constant.Properties.VehicleState;

@Entity
@Table(name = "vehicle")
public class Vehicle extends PropertyEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	@Column(name = "model")
	private String model;
	
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9]*$") // REVIEW is this the right pattern for plate number?
	@Column(name = "plate_number")
	private String plateNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "payment_id")
	private Payment payment;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "park_id")
	private Park park;
	
	@Transient
	private String state;

	public Vehicle(){};

	public Vehicle(String model, String plateNumber, Payment payment, Park park, String state) {
		super();
		this.model = model;
		this.plateNumber = plateNumber;
		this.payment = payment;
		this.park = park;
		this.state = state;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public VehicleState getState() {
		return this.park == null ? VehicleState.OUTPARK : VehicleState.INPARK;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public boolean isPaid() {
		return this.payment != null;
	}
	
}
