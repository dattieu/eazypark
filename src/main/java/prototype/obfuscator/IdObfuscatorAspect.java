package prototype.obfuscator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IdObfuscatorAspect {
	
	private final Hashids idObfuscator;
	
	@Autowired
	public IdObfuscatorAspect(Hashids idObfuscator) {
		this.idObfuscator = idObfuscator;
	}
	
	@Around("execution(* prototype.controller.*.*(@org.springframework.web.bind.annotation.PathVariable (*), ..))")
	public Object getDeObfuscatedId(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] arguments = joinPoint.getArgs();
		String obfuscatedId = (String) arguments[0];
		arguments[0] = String.valueOf(idObfuscator.decode(obfuscatedId)[0]); // return the deObfuscated Id back into arguments
		return joinPoint.proceed(arguments);
	}

}
