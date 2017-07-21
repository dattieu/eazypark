package prototype.service;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import prototype.dao.impl.PaymentDaoImpl;
import prototype.model.Payment;
import prototype.service.impl.PaymentServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTests {

	@Spy
	private PaymentDaoImpl paymentDao;
	
	@InjectMocks
	private PaymentServiceImpl paymentService;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private final Payment payment = new Payment("prepaid", new BigDecimal(100));
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);	
		
		// paymentDao will use id to get payment from database
		payment.setId(1);
		
		// Mock data for database payment
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				int paymentId = (Integer) invocation.getArguments()[0];
				
				if(paymentId == 1) {
					return payment;
				}
				return null;
			}
		}).when(paymentDao).getById(anyInt());
	}
	
	@Test
	public void updatePaymentSuccessTest() {
		// Given
		doNothing().when(paymentDao).update(any(Payment.class));
		
		// When
		paymentService.updatePayment(payment);
		
		// Then
		assertEquals(new BigDecimal(200), payment.getAmount());
	}
	
	@Test
	public void updatePaymentFailureTest() {
		
	}
	
}
