package prototype.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prototype.model.Role;
import prototype.model.User;
import prototype.service.UserService;
 
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	private static final boolean ENABLE = true;
	private static final boolean ACCOUNT_NON_EXPIRED = true;
	private static final boolean CREDENTIALS_NON_EXPIRED = true;
	private static final boolean ACCOUNT_NON_LOCKED = true;
	
    private final UserService userService;
    
    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
		super();
		this.userService = userService;
	}

	@Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByKey("email", email);
  
        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), 
        		ENABLE, ACCOUNT_NON_EXPIRED, CREDENTIALS_NON_EXPIRED, ACCOUNT_NON_LOCKED, getGrantedAuthorities(user));
    }
 
    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        
        return authorities;
    }
     
}
