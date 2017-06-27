package prototype.exception;

public class InvalidPayment extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidPayment(String message) {
		super(message);
	}

}
