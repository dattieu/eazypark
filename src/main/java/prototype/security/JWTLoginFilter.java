package prototype.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import prototype.model.User;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	protected final static Logger logger = Logger.getLogger(JWTLoginFilter.class);
	
	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException, IOException, ServletException {
		
		User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		logger.info("Authenticating user: " + user.getEmail() + ", password " + user.getPassword());
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, 
			Authentication auth) throws IOException, ServletException {
		logger.info("Creating JWT for user " + auth.getName());
		TokenAuthenticationService.addAuthentication(response, auth.getName());
	}
}
