package prototype.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordEncodingGenerator {
	
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public static final String encode(String password){
    	return passwordEncoder.encode(password);
    }
    
    public static final boolean match(String rawPassword, String encodedPassword ){
    	return passwordEncoder.matches(rawPassword, encodedPassword);
    }
 
}
