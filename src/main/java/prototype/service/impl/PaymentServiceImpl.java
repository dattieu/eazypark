package prototype.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import prototype.dao.GenericDao;
import prototype.dao.PaymentDao;
import prototype.model.Payment;
import prototype.service.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl extends GenericServiceImpl<Payment, Integer> implements PaymentService {

	private final PaymentDao paymentDao;
	
	@Autowired
	public PaymentServiceImpl(@Autowired GenericDao<Payment, Integer> genericDao) {
		super(genericDao);
		this.paymentDao = (PaymentDao) genericDao;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void renewPayment(Payment payment) {
		Payment dbPayment = paymentDao.getById(payment.getId());
		
		if(dbPayment == null) {
			throw new EntityNotFoundException("Payment not found");
		}
		
		dbPayment.setAmount(payment.getAmount());
		paymentDao.update(dbPayment);
	}

}
