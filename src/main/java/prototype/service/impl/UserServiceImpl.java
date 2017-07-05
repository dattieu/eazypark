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
		String userEmail = user.getEmail();
		User dbUser = userDao.getByKey("email", userEmail);
		
		if(dbUser == null){
			throw new EntityNotFoundException("User email " + userEmail + " not found");
		}
		return passwordEncoder.matches(user.getPassword(), dbUser.getPassword());							
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void register(User user) {
		String userEmail = user.getEmail();
		if(existUser(userEmail)){
			throw new EntityExistsException("User " + userEmail + " already exists");
		}
		
		// REVIEW need to set Roles here? is it good to use the 'new' constructor here?
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setProfile(new Profile());
		userDao.save(user);
	}
	
	private boolean existUser(String userEmail) {
		return userDao.getByKey("email", userEmail) == null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void changePassword(PasswordChangeDto pwdChangeDto) {
		String userEmail = pwdChangeDto.getEmail();
		User dbUser = userDao.getByKey("email", userEmail);
		
		if(dbUser == null){
			throw new EntityNotFoundException("User email " + userEmail + " not found");
		}
		
		dbUser.setPassword(passwordEncoder.encode(pwdChangeDto.getNewPassword()));
		userDao.update(dbUser);
	}

}
