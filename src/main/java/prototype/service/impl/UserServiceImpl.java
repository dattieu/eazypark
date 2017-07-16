package prototype.service.impl;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import prototype.dao.GenericDao;
import prototype.dao.UserDao;
import prototype.dto.PasswordChangeDto;
import prototype.model.Profile;
import prototype.model.User;
import prototype.service.UserService;

@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

	private final UserDao userDao;
	
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(@Autowired GenericDao<User, String> genericDao, PasswordEncoder passwordEncoder) {
		super(genericDao);
		this.userDao = (UserDao) genericDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean login(User user) {
		User dbUser = getUserByEmail(user.getEmail());
		return passwordEncoder.matches(user.getPassword(), dbUser.getPassword());							
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void register(User user) {
		if(existUser(user)){
			throw new EntityExistsException("User " + user.getEmail() + " already exists");
		}
		
		// REVIEW need to set Roles here?
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setProfile(new Profile());
		userDao.save(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void changePassword(PasswordChangeDto pwdChangeDto) {
		User dbUser = getUserByEmail(pwdChangeDto.getUserEmail());
		dbUser.setPassword(passwordEncoder.encode(pwdChangeDto.getNewPassword()));
		userDao.update(dbUser);
	}
	
	private User getUserByEmail(String userEmail) {
		User dbUser = userDao.getByKey("email", userEmail);
		
		if(dbUser == null){
			throw new EntityNotFoundException("User email " + userEmail + " not found");
		}
		
		return dbUser;
	}
	
	private boolean existUser(User user) {
		return userDao.getByKey("email", user.getEmail()) !=  null;
	}

}
