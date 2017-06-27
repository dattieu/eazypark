package prototype.dao.impl;

import org.springframework.stereotype.Repository;

import prototype.dao.PaymentDao;
import prototype.model.Payment;

@Repository("paymentDao")
public class PaymentDaoImpl extends GenericDaoImpl<Payment, Integer> implements PaymentDao {
	
}
