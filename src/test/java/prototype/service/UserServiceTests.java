package prototype.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import javax.persistence.EntityExistsException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import prototype.dao.impl.UserDaoImpl;
import prototype.model.User;
import prototype.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
	
	// If using @Mock, it would return null, because genericDao is the one that actually carry on the getByKey() (super class)
	// If changing all the userDao in the Service code to genericDao, @Mock will work
	// Due to the complex of the code of using generic DAO and generic Service, got to use @Spy to get the 
	// real object and then mock the required behaviors from the super class (partial @Mock)
	@Spy
	private UserDaoImpl userDao;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private User user = new User("admin@gmail.com", "admin123", null, null);
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		// Mock data for database user
		doReturn(user).when(userDao).getByKey(anyString(), anyString());
	}
	
	@Test
	public void loginUserSuccessTest() {
		// Given
		// Can not @Spy (due to passworkEncoder is just an interface), @Mock not work here?!
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		
		// When
		Boolean loginResult = userService.login(user);
		
		// Then
		assertEquals(true, loginResult);
		verify(userDao, times(1)).getByKey("email", "admin@gmail.com");
	}
	
	@Test
	public void registerUserFailureTest() {
		// Given
		when(passwordEncoder.encode(anyString())).thenReturn("xxxxxx");
		doNothing().when(userDao).save(any(User.class));
		
		// When
		exception.expect(EntityExistsException.class);
		userService.register(user);
	}
	
}
