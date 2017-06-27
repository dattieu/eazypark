package prototype.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import prototype.validator.Address;

@Entity
@Table(name = "park")
public class Park extends PropertyEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Address
	@Column(name = "address")
	private String address;
	
	@Digits(integer = 10, fraction = 0)
	@Column(name = "tel")
	private String telephone;
	
	@Min(0)
	@Column(name = "capacity")
	private int capacity;
	
	@Min(0)
	@Column(name = "fee")
	private BigDecimal fee;
	
	@Transient
	private int vacancy;
	
	@OneToMany(mappedBy = "park", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<Vehicle> vehicleList;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "park_review", joinColumns = @JoinColumn(name = "park_id"), inverseJoinColumns = @JoinColumn(name = "review_id"))
	private Set<Review> reviewList;
	
	@Embedded
	private WorkingTime workingTime;
	
	@Embedded
	private Coordinate coordinate;

	public Park(){};

	public Park(String address, String telephone, int capacity, BigDecimal fee, int vacancy, Set<Vehicle> vehicleList,
			Set<Review> reviewList, WorkingTime workingTime, Coordinate coordinate) {
		super();
		this.address = address;
		this.telephone = telephone;
		this.capacity = capacity;
		this.fee = fee;
		this.vacancy = vacancy;
		this.vehicleList = vehicleList;
		this.reviewList = reviewList;
		this.workingTime = workingTime;
		this.coordinate = coordinate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public WorkingTime getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(WorkingTime workingTime) {
		this.workingTime = workingTime;
	}

	public int getVacancy() {
		return this.capacity - getVehicleListInternal().size();
	}

	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}
	
	protected Set<Vehicle> getVehicleListInternal() {
		return vehicleList == null ? Collections.<Vehicle>emptySet() : vehicleList;
	}
	
	protected void setVehicleListInternal(Set<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
	
	public List<Vehicle> getVehicleList() {
		List<Vehicle> sortedVehicleList = new ArrayList<Vehicle>(getVehicleListInternal());
        PropertyComparator.sort(sortedVehicleList, new MutableSortDefinition("park_id", true, true));
        return Collections.unmodifiableList(sortedVehicleList);
	}

	public void addVehicle(Vehicle vehicle) {
		if(vehicle.isNew()){
			getVehicleListInternal().add(vehicle);
		}
		vehicle.setPark(this);
	}
	
	public void removeVehicle(Vehicle vehicle) {
		getVehicleListInternal().remove(vehicle);
	}

	public Vehicle getVehicle(String plateNumber) {
        for (Vehicle vehicle : getVehicleListInternal()) {
        	if(vehicle.getPlateNumber().equals(plateNumber)) {
        		return vehicle;
        	}
        }
        return new Vehicle();
	}
	
	public boolean isFull() {
		return getVacancy() == 0;
	}
	
	public boolean isOpenNow() {
		LocalTime now = LocalTime.now();
		WorkingTime workingTime = getWorkingTime();
		// REVIEW default: workingTime = null means that park always opens
		return (workingTime == null) ? true : now.isAfter(workingTime.getFromTime()) && now.isBefore(workingTime.getToTime());
	}

	protected Set<Review> getReviewListInternal() {
		return reviewList == null ? Collections.<Review>emptySet() : reviewList;
	}
	
	protected void setReviewListInternal(Set<Review> reviewList) {
		this.reviewList = reviewList;
	}
	
	public List<Review> getReviewList() {
		List<Review> sortedReviewList = new ArrayList<Review>(getReviewListInternal());
        PropertyComparator.sort(sortedReviewList, new MutableSortDefinition("owner", true, true));
        return Collections.unmodifiableList(sortedReviewList);
	}
	
	public void addReview(Review review) {
		getReviewListInternal().add(review);
	}

}
