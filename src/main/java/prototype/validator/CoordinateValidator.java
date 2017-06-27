package prototype.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import prototype.model.Coordinate;

@Component
public class CoordinateValidator implements Validator {
	
	private static final String INVALID = "invalid";

	public boolean supports(Class<?> clazz) {
		return Coordinate.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Coordinate coordinate = (Coordinate) target;
		double latitude = coordinate.getLatitude();
		double longitude = coordinate.getLongitude();
		
		if(latitude < -90 || latitude > 90) {
			errors.rejectValue("latitude", INVALID);
		}
		
		if(longitude < -180 || longitude > 180) {
			errors.rejectValue("longitude", INVALID);
		}
	}

}
