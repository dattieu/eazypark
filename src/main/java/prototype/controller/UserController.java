package prototype.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.model.User;
import prototype.service.UserService;

@RestController
public class UserController {
	
	private static final String LOGIN = "/login";
	private static final String USERS = "/users";
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(LOGIN)
	public boolean login(@RequestBody @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_USER);
		}
		return userService.login(user);
	}
	
	@PostMapping(USERS)
	public void registerNewUser(@RequestBody @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_USER);
		}
		userService.register(user);
	}
	
	@GetMapping(USERS)
	public User getUser(@RequestParam(value = "email", required = true) String email) {
		// TODO handle null object? -> Null object pattern? or throw exception? where? service or DAO layer or here?
		// REVIEW only admin can get this (need to start Spring Security and do some obsfucation work here)
		return userService.getByKey("email", email);
	}
	
}
