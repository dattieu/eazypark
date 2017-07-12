package prototype.obfuscator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IdObfuscator {
	
	private final Hashids idObfuscator;
	
	@Autowired
	public IdObfuscator(Hashids idObfuscator) {
		this.idObfuscator = idObfuscator;
	}
	
//	@Around("execution(* public protoype.controller.*.get*(String, ...))")
	@Around("execution(* prototype.controller.ParkController.getParkById(..))")
	public Object getDeObfuscatedId(ProceedingJoinPoint joinPoint) throws Throwable {
		String obfuscatedId = (String) joinPoint.getArgs()[0];
		
		// REVIEW because of the fact that it gets in a String, so it has to return a String (due to intercepted method signature)
		// REVIEW any better way to do this?
		// TODO find a way to implement this for all controllers and logging system too
		String deObfuscatedParkId = String.valueOf(idObfuscator.decode(obfuscatedId)[0]);
		return joinPoint.proceed(new Object[] {deObfuscatedParkId});
	}

}
