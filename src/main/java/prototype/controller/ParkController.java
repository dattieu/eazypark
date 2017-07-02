package prototype.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.model.Coordinate;
import prototype.model.Park;
import prototype.service.ParkService;
import prototype.validator.CoordinateValidator;

@RestController
public class ParkController {

	private static final String PARK = "/parks";
	private static final String PARK_BY_ID= "/parks/{id}";
	private static final String NEAREST_PARK = "/nearest_parks";
	
	private final ParkService parkService;
	private final CoordinateValidator coordinateValidator;
	
	@Autowired
	public ParkController(ParkService parkService, CoordinateValidator coordinateValidator) {
		this.parkService = parkService;
		this.coordinateValidator = coordinateValidator;
	}
	
	@PostMapping(PARK)
	public void registerNewPark(@RequestBody @Valid Park park, BindingResult result) {
		// REVIEW do both bean validation via annotation and Spring validation
		// TODO need testing
		coordinateValidator.validate(park.getCoordinate(), result);
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_PARK);
		}
		parkService.register(park);
	}
	
	@GetMapping(PARK_BY_ID)
	public Park getParkById(@PathVariable("id") int parkId) {
		return parkService.getById(parkId);
	}
	
	@GetMapping(NEAREST_PARK)
	// REVIEW request parameters will be wired automatically to the object if omit @RequestParam
	// REVIEW URI: /nearest_parks?latitude=0&longitude=0
	public List<Park> getNearParks(@Valid Coordinate userLocation, BindingResult result) {
		// REVIEW coordinate validation
		coordinateValidator.validate(userLocation, result);
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_COORDINATE);
		}
		return parkService.getNearParks(userLocation);
	}
	
}
