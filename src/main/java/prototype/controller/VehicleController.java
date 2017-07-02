package prototype.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public void registerNewVehicle(@RequestBody @Valid Vehicle vehicle, BindingResult result) {
		// REVIEW due to Primary Shared Key between Vehicle and Payment, they must be updated together
		// REVIEW client have to do payment as soon as registering new vehicle or else what is the whole point to do that?
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_VEHICLE);
		}
		vehicleService.register(vehicle);
	}
	
	@GetMapping(VEHICLE)
	public Vehicle getVehicle(@RequestParam(value = "plateNumber", required = true) String plateNumber) {
		// TODO how to do validation here
		// TODO handle null object? -> Null object pattern? or throw exception? where? service or DAO layer or here?
		return vehicleService.getByKey("plateNumber", plateNumber);
	}
	
}
