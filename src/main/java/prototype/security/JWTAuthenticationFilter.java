package prototype.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JWTAuthenticationFilter implements Filter {

	protected final static Logger logger = Logger.getLogger(JWTLoginFilter.class);
	
	private final TokenAuthenticationService authenticationService;

	public JWTAuthenticationFilter(TokenAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    	Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	logger.info("Authentication " + authentication.getName());
    	filterChain.doFilter(request, response);
    }

	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
	
}
