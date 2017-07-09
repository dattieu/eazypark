package prototype.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JWTAuthenticationFilter implements Filter {

	private final TokenAuthenticationService authenticationService;

	public JWTAuthenticationFilter(TokenAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    	Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	SecurityContextHolder.getContext().setAuthentication(null);
    	filterChain.doFilter(request, response);
    }

	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
	
}
