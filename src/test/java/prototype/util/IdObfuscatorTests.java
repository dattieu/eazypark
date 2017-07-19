package prototype.util;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

// Just basic jUnit test demo
public class IdObfuscatorTests {
	
	@Test
	public void testObfuscationMethod() {
		assertEquals(IdObfuscator.obfuscate(1), "kXG8");
	}
	
	@Test
	public void testDeObfuscationMethod() {
		assertEquals(IdObfuscator.deObfuscate("kXG8"), 1);
	}

}
