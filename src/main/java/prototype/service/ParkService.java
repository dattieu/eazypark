package prototype.service;

import java.util.List;

import prototype.model.Coordinate;
import prototype.model.Park;

public interface ParkService extends GenericService<Park, String> {
	
	void register(Park park);
	List<Park> getNearParks(Coordinate coordinate);
	
}
