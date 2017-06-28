package prototype.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<Address, String> {
	
	private static final int NUMBER_OF_ADDRESS_FILEDS = 5;
	private static final String ADDRESS_START_WITH_NUMBER = "^[0-9]*$";

	public void initialize(Address constraintAnnotation) {
		
	}

	// REVIEW valid address pattern: "123B, Baker Street, Hell Kitchen, Manhattan, New York "
	public boolean isValid(String address, ConstraintValidatorContext context) {
		String[] addressParts = address.split(",");
		if(addressParts.length != NUMBER_OF_ADDRESS_FILEDS) {
			return false;
		}
		
		String addressNumber = addressParts[0];
		if(addressNumber.matches(ADDRESS_START_WITH_NUMBER)) {
			return false;
		}
		
		return false;
	}

}