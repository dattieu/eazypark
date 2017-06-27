package prototype.service;

import prototype.model.Vehicle;

public interface VehicleService extends GenericService<Vehicle, String> {

	void register(Vehicle vehicle);
	
}
