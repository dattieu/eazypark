package prototype.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TokenAuthenticationService {

	protected final static Logger logger = Logger.getLogger(JWTLoginFilter.class);
	
	private static final String HEADER_STRING = "Authorization";
	
	private static TokenHandler tokenHandler = new TokenHandler();

    public static void addAuthentication(HttpServletResponse response, String userEmail) {
        response.addHeader(HEADER_STRING, tokenHandler.createTokenForUser(userEmail));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
    	final String token = request.getHeader(HEADER_STRING);
        
        if (token != null) {
        	String userEmail = tokenHandler.parseUserFromToken(token);
        	logger.info("Token " + token + ", user email " + userEmail);
        	
            if (userEmail != null) {
            	return new UsernamePasswordAuthenticationToken(userEmail, null);
            }
        }
        
        return null;
    }
    
}