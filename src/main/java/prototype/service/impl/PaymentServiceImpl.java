package prototype.service.impl;

import java.math.BigDecimal;

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
	public void updatePayment(Payment payment) {
		// REVIEW just simple calculation, regardless of 'prepaid' or 'credit', not real world actual payment
		Payment dbPayment = getPaymentById(payment.getId());
		BigDecimal newAmount = calculateNewPaymentAmount(payment, dbPayment);
		dbPayment.setAmount(newAmount);
		paymentDao.update(dbPayment);
	}
	
	private Payment getPaymentById(int paymentId) {
		Payment dbPayment = paymentDao.getById(paymentId);
		
		if(dbPayment == null) {
			throw new EntityNotFoundException("Payment " + paymentId + " not found");
		}
		
		return dbPayment;
	}
	
	private BigDecimal calculateNewPaymentAmount(Payment newPayment, Payment currentPayment) {
		return currentPayment.getAmount().add(newPayment.getAmount());
	}

}
