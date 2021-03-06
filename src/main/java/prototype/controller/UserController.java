package prototype.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.dto.PasswordChangeDto;
import prototype.model.User;
import prototype.service.UserService;

@RestController
public class UserController {
	
	private static final String LOGIN = "/login";
	private static final String LOGOUT = "/logout";
	private static final String USERS = "/users";
	private static final String PASSWORD_CHANGE = "/password_change";
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(LOGIN)
	public ResponseEntity<String> login(@RequestBody @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_USER);
		}
		return (userService.login(user) == true) ? new ResponseEntity<String>(HttpStatus.OK) 
				: new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping(LOGOUT)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}
	
	@PostMapping(USERS)
	@ResponseStatus(HttpStatus.CREATED)
	public void registerNewUser(@RequestBody @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_USER);
		}
		userService.register(user);
	}
	
	@GetMapping(USERS)
	public ResponseEntity<User> getUser(@RequestParam(value = "email", required = true) String email) {
		// REVIEW only admin can get this (need to provide authentication)
		User user = userService.getByKey("email", email);
		return (user != null) ? new ResponseEntity<User>(user, HttpStatus.OK) : new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(PASSWORD_CHANGE)
	public void changeUserPassword(@RequestBody @Valid PasswordChangeDto pwdChangeDto, BindingResult result) {
		// TODO password matching validation
		// TODO need testing
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_PASSSWORD_CHANGE);
		}
		userService.changePassword(pwdChangeDto);
	}
	
}
