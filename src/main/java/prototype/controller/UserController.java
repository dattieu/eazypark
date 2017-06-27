package prototype.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import prototype.model.User;
import prototype.service.UserService;

@RestController
public class UserController {

	private final UserService userService;
	
	private static final String LOGIN = "/login";
	private static final String USERS = "/users";
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(LOGIN)
	public boolean login(@RequestBody @Valid User user, BindingResult result) {
		if(result.hasErrors()) {
            return false;
		}
		return userService.login(user);
	}
	
	@PostMapping(USERS)
	public boolean registerNewUser(@RequestBody @Valid User user, BindingResult result) {
		// TODO what should be returned here? boolean or void and throw exception instead
		if(result.hasErrors()) {
            return false;
		}
		userService.register(user);
		return true;
	}
	
	@GetMapping(USERS)
	public User getUser(@RequestParam(value = "email", required = true) String email) {
		// TODO handle null object? -> Null object pattern? or throw exception? where? service or DAO layer or here?
		// TODO handle security here? => need URI obfuscation
		return userService.getByKey("email", email);
	}
	
}