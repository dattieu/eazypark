package prototype.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.apache.log4j.NDC;
import org.springframework.stereotype.Component;

import prototype.model.Payment;

@Aspect
@Component
public class PaymentTransactionLogger {

	// REVIEW works but how to configure log4j to see the log?
	@Around("execution(* prototype.controller.PaymentController.updatePayment(..))")
	public Object getDeObfuscatedId(ProceedingJoinPoint joinPoint) throws Throwable {
		Payment payment = (Payment) joinPoint.getArgs()[0];
		
		NDC.push("Payment ID: " + payment.getId() + ", type: " + payment.getType() + ", amount: " + payment.getAmount());
		NDC.pop();
		NDC.remove();
		
		return joinPoint.proceed();
	}
	
}
