package prototype.service.impl;

import org.springframework.stereotype.Service;
import prototype.model.Payment;
import prototype.service.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl extends GenericServiceImpl<Payment, Integer> implements PaymentService {

}
