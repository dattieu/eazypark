package prototype.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.model.Vehicle;
import prototype.service.VehicleService;

@RestController
public class VehicleController {
	
	private static final String VEHICLE = "/vehicles";

	private final VehicleService vehicleService;
	
	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	
	@PostMapping(VEHICLE)
	@ResponseStatus(HttpStatus.CREATED)
	public void registerNewVehicle(@RequestBody @Valid Vehicle vehicle, BindingResult result) {
		// REVIEW due to Primary Shared Key between Vehicle and Payment, they must be saved together
		// REVIEW client have to do payment as soon as registering new vehicle or else what is the whole point to do that?
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_VEHICLE);
		}
		vehicleService.register(vehicle);
	}
	
	@GetMapping(VEHICLE)
	public ResponseEntity<Vehicle> getVehicle(@RequestParam(value = "plateNumber", required = true) String plateNumber) {
		// TODO how to do validation here
		Vehicle vehicle = vehicleService.getByKey("plateNumber", plateNumber);
		return (vehicle != null) ? new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK) 
				: new ResponseEntity<Vehicle>(vehicle, HttpStatus.NOT_FOUND);
	}
	
}
