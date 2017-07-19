package prototype.service;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import prototype.dao.impl.UserDaoImpl;
import prototype.model.User;
import prototype.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

	@Mock
	private UserDaoImpl userDao;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	// REVIEW Not Work! Mock returns null object! Why? unmockable when using GenericDao?
	@Test
	public void loginSuccessTest() {
		
		// Given
		User user = new User("admin@gmail.com", "admin123", null, null);
		when(userDao.getByKey(eq("email"), eq("admin@gmail.com"))).thenReturn(user);
		
		// When
		Boolean loginResult = userService.login(user);
		
		// Then
		assertEquals(true, loginResult);
		verify(userDao, times(1)).getByKey("email", "admin@gmail.com");
		
	}
	
	// REVIEW Not Work! Mock returns null object! Why? unmockable when using GenericDao?
	@Test
	public void registerUserSuccessTest() {
		
		// Given
		User user = new User("admin@gmail.com", "admin123", null, null);
		when(userDao.getByKey(eq("email"), eq("admin@gmail.com"))).thenReturn(user);
		when(passwordEncoder.encode(anyString())).thenReturn("xxxxxx");
		doNothing().when(userDao).save(any(User.class));
		
		// When
		userService.register(user);
		
		// Then
		verify(userDao, times(1)).save(any(User.class));
		
	}
	
	// TODO debugging why it fails when mocking through the service layer
	@Test
	public void debugMockitoTest() {
		// Given
		when(userDao.getByKey("email", "admin@gmail.com")).thenReturn(new User("admin@gmail.com", "admin123", null, null));
		
		// Work
		User dbUser = userDao.getByKey("email", "admin@gmail.com");
		verify(userDao, times(1)).getByKey("email", "admin@gmail.com");
		assertNotNull(dbUser);
		
		// Not work! mock returns null when calling from service layer, why can't it mock?
		Boolean loginResult = userService.login(new User("admin@gmail.com", "admin123", null, null));
		verify(userDao, times(1)).getByKey("email", "admin@gmail.com");
		assertEquals(true, loginResult);
		
	}
	
}
