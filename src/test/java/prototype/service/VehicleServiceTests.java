package prototype.service;

import static org.mockito.Mockito.*;

import javax.persistence.EntityExistsException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import prototype.dao.impl.VehicleDaoImpl;
import prototype.model.Vehicle;
import prototype.service.impl.VehicleServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTests {

	@Spy
	private VehicleDaoImpl vehicleDao;
	
	@InjectMocks
	private VehicleServiceImpl vehicleService;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private Vehicle vehicle = new Vehicle("car", "DAT92", null, null, "");
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);	
		
		// Mock data for database vehicle
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				String plateNumber = (String) invocation.getArguments()[1];
				
				if(plateNumber.equals("DAT92")) {
					return vehicle;
				}
				return null;
			}
		}).when(vehicleDao).getByKey(anyString(), anyString());
	}
	
	@Test
	public void registerVehicleSuccessTest() {
		// Given
		Vehicle newVehicle = new Vehicle("car", "JDOE17", null, null, "");
		doNothing().when(vehicleDao).save(any(Vehicle.class));
		
		// When
		vehicleService.register(newVehicle);
		
		// Then
		verify(vehicleDao, times(1)).save(newVehicle);
	}
	
	@Test
	public void registerVehicleFailureTest() {
		// When
		exception.expect(EntityExistsException.class);
		vehicleService.register(vehicle);
	}
	
}
