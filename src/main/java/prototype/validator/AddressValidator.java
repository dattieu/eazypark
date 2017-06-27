package prototype.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<Address, String> {

	public void initialize(Address constraintAnnotation) {
		
	}

	// REVIEW valid address pattern: "123B, Baker Street, Hell Kitchen, Manhattan, New York "
	public boolean isValid(String address, ConstraintValidatorContext context) {
		String[] addressParts = address.split(",");
		if(addressParts.length != 5) {
			return false;
		}
		
		String addressNumber = addressParts[0];
		if(addressNumber.matches("^[0-9]*$")) {
			return false;
		}
		
		return false;
	}

}