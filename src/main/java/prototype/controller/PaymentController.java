package prototype.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.model.Payment;
import prototype.service.PaymentService;

@RestController
public class PaymentController {

	private static final String PAYMENTS = "/payments";
	
	private final PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PutMapping(PAYMENTS)
	public void updatePayment(@RequestBody @Valid Payment payment, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_PAYMENT);
		}
		paymentService.updatePayment(payment);
	}
	
}
