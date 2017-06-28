package prototype.service.impl;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import prototype.constant.Properties.VehicleState;
import prototype.exception.InvalidPayment;
import prototype.model.Park;
import prototype.model.Payment;
import prototype.model.Vehicle;
import prototype.service.ParkService;
import prototype.service.PaymentService;
import prototype.service.ScannerService;
import prototype.service.VehicleService;

@Service("scannerService")
@Transactional(propagation = Propagation.REQUIRED)
public class ScannerServiceImpl implements ScannerService {

	private final PaymentService paymentService;
	private final VehicleService vehicleService;
	private final ParkService parkService;
	
	@Autowired
	public ScannerServiceImpl(PaymentService paymentService, VehicleService vehicleService, ParkService parkService) {
		super();
		this.paymentService = paymentService;
		this.vehicleService = vehicleService;
		this.parkService = parkService;
	}
	
	// REVIEW need revising this "scanning" business logic
	public void scan(String plateNumber, String parkId) {
		Vehicle vehicle = vehicleService.getByKey("plate_number", plateNumber);
		Park park = parkService.getByKey("id", parkId);
		
		if(vehicle == null){
			throw new EntityNotFoundException("Vehicle with plate number " + plateNumber + " not found");
		}
		
		if(isVehicleInPark(vehicle, parkId)) {
			checkout(vehicle, park);
		}
		else {
			checkin(vehicle, park);
		}
	}

	private boolean isVehicleInPark(Vehicle vehicle, String parkId) {
		VehicleState vehicleCurrentState = vehicle.getState();
		Park currentPark = vehicle.getPark();
		
		if(vehicleCurrentState.equals(VehicleState.INPARK)){
			return  currentPark.getId().equals(parkId);
		}
		return false;
	}
	
	private void checkout(Vehicle vehicle, Park park) {
		park.removeVehicle(vehicle);
		parkService.update(park);
		vehicle.setPark(null);
		vehicleService.update(vehicle);
		chargeFee(vehicle, park);
	}
	
	private void checkin(Vehicle vehicle, Park park) {
		park.addVehicle(vehicle);
		parkService.update(park);
		vehicle.setPark(park);
		vehicleService.update(vehicle);
	}
	
	private void chargeFee(Vehicle vehicle, Park park) {
		Payment payment = vehicle.getPayment();
		BigDecimal accountBalance = payment.getAmount();
		BigDecimal parkFee = park.getFee();
		
		if(accountBalance.compareTo(parkFee) == -1) {
			throw new InvalidPayment("Not enough money in account to charge");
		}
		
		payment.setAmount(accountBalance.subtract(parkFee));
		paymentService.update(payment);
	}
	
}
