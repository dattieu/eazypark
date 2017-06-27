package prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import prototype.service.PaymentService;

@RestController
public class PaymentController {

	private static final String PAYMENT_BY_VEHICLE_ID = "/payments/{vehicleId}";
	
	private final PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
}
