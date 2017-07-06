package prototype.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import prototype.dao.GenericDao;
import prototype.dao.ParkDao;
import prototype.model.Coordinate;
import prototype.model.Park;
import prototype.service.ParkService;
import prototype.util.Haversine;

@Service("parkService")
public class ParkServiceImpl extends GenericServiceImpl<Park, String> implements ParkService {
	
	private static final double NEAREST_PARK_RADIUS = 1.0;

	private final ParkDao parkDao;
	
	@Autowired
	public ParkServiceImpl(@Autowired GenericDao<Park, String> genericDao) {
		super(genericDao);
		this.parkDao = (ParkDao) genericDao;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void register(Park park) {
		// TODO how to validate valid address, need address uniform?
		if(existPark(park)){
			throw new EntityExistsException("Park at this address " + park.getAddress() + " already registered");
		}
		parkDao.save(park);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Park> getNearParks(Coordinate userCoordinate) {
		List<Park> allParksInDb = parkDao.getAll();
		List<Park> nearestParks = new ArrayList<Park>();
		
		// REVIEW use Haversine formula to calculate distance between each park and user location. Better idea?
		// REVIEW how about Google Places API for java?
		for(Park park : allParksInDb){
			Coordinate parkCoordinate = park.getCoordinate();
			double distance = Haversine.getDistance(parkCoordinate.getLatitude(), parkCoordinate.getLongitude(), 
					userCoordinate.getLatitude(), userCoordinate.getLongitude());
			
			if(distance <= NEAREST_PARK_RADIUS){
				nearestParks.add(park);
			}
		}
		
		return nearestParks;
	}
	
	private boolean existPark(Park park) {
		return parkDao.getByKey("address", park.getAddress()) == null;
	}

}
