package prototype.config;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import prototype.security.JWTAuthenticationFilter;
import prototype.security.JWTLoginFilter;
import prototype.security.TokenAuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String REALM = "EAZY_PARK";
	private static final String ID_HASH_SALT = "salt"; // REVIEW should it be here?
	private static final int OBFUSCATED_ID_MIN_LENGTH = 4;
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
    
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
	@Bean
	public Hashids getIdObfuscator() {
		 return new Hashids(ID_HASH_SALT, OBFUSCATED_ID_MIN_LENGTH);
	}
	
	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		return new CustomBasicAuthenticationEntryPoint();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// REVIEW JWT only works for the first time!, the token expired and wouldn't work again 
		// REVIEW JWT TokenHandler always generates the same token for all users every single time, what goes wrong?!
		// REVIEW how to implement refresh token? just a mess here!
		// REVIEW just remove the filters for JWT and it's good to go with normal basic authentication
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/").permitAll() 
	        .antMatchers(HttpMethod.GET, "/users/**").access("hasRole('ADMIN')")
	        .anyRequest().authenticated().and()
	        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
	        .addFilterBefore(new JWTAuthenticationFilter(new TokenAuthenticationService()), 
	        		UsernamePasswordAuthenticationFilter.class)
	        .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
}
