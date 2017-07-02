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
		String loginEmail = user.getEmail();
		User dbUser = userDao.getByKey("email", loginEmail);
		
		if(dbUser == null){
			throw new EntityNotFoundException("User email " + loginEmail + " not found");
		}
		return passwordEncoder.matches(user.getPassword(), dbUser.getPassword());							
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void register(User user) {
		if(existUser(user)){
			throw new EntityExistsException("User " + user.getEmail() + " already exists");
		}
		
		// REVIEW need to set Roles here? is it good to use the 'new' constructor here?
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setProfile(new Profile());
		userDao.save(user);
	}
	
	private boolean existUser(User user) {
		return userDao.getByKey("email", user.getEmail()) == null;
	}

	public void changePassword(String newPassword) {
		// TODO Auto-generated method stub
	}

}
