package prototype.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import prototype.model.Vehicle;
import prototype.service.VehicleService;

@RestController
public class VehicleController {

	private final VehicleService vehicleService;
	
	private static final String VEHICLE = "/vehicles";
	
	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	
	@PostMapping(VEHICLE)
	public boolean registerNewVehicle(@RequestBody @Valid Vehicle vehicle, BindingResult result) {
		// REVIEW due to Primary Shared Key between Vehicle and Payment, they must be updated together
		// REVIEW client have to do payment as soon as registering new vehicle or else what is the whole point to do that?
		// TODO what should be returned here? boolean or void and throw exception instead
		if(result.hasErrors()) {
            return false;
		}
		vehicleService.register(vehicle);
		return true;
	}
	
	@GetMapping(VEHICLE)
	public Vehicle getVehicle(@RequestParam(value = "plateNumber", required = true) String plateNumber) {
		// TODO handle null object? -> Null object pattern? or throw exception? where? service or DAO layer or here?
		return vehicleService.getByKey("plateNumber", plateNumber);
	}
	
}
