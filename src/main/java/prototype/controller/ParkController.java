package prototype.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ResponseStatus(HttpStatus.CREATED)
	public void registerNewPark(@RequestBody @Valid Park park, BindingResult result) {
		// TODO need testing
		coordinateValidator.validate(park.getCoordinate(), result);
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_PARK);
		}
		parkService.register(park);
	}
	
	@GetMapping(PARK_BY_ID)
	public ResponseEntity<Park> getParkById(@PathVariable("id") String parkId) {
		// TODO just a draft version, need some cleaning and figure a solution to apply this for all controllers
		Park park = parkService.getById((Integer.parseInt(parkId)));
		return (park != null) ? new ResponseEntity<Park>(park, HttpStatus.OK) : new ResponseEntity<Park>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(NEAREST_PARK)
	// REVIEW request parameters will be wired automatically to the object if omit @RequestParam
	// REVIEW URI: /nearest_parks?latitude=0&longitude=0
	public ResponseEntity<List<Park>> getNearParks(@Valid Coordinate userLocation, BindingResult result) {
		coordinateValidator.validate(userLocation, result);
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_COORDINATE);
		}
		List<Park> nearestParks = parkService.getNearParks(userLocation);
		return (!nearestParks.isEmpty()) ? new ResponseEntity<List<Park>>(nearestParks, HttpStatus.OK) 
				: new ResponseEntity<List<Park>>(HttpStatus.NOT_FOUND);
	}
	
}
