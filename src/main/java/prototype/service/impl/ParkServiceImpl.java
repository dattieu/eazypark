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

	private final ParkDao parkDao;
	
	private static final double NEAREST_PARK_RADIUS = 1.0;
	
	@Autowired
    public ParkServiceImpl(@Autowired GenericDao<Park, String> genericDao) {
		super(genericDao);
        this.parkDao = (ParkDao) genericDao;
    }
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void register(Park park) {
		String registeringParkAddress = park.getAddress();
		Park dbPark = parkDao.getByKey("address", registeringParkAddress);
		
		// TODO how to validate valid address, need address uniform?
		if(dbPark != null){
			throw new EntityExistsException("Park at this address " + registeringParkAddress + " already registered");
		}
		parkDao.save(park);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Park> getNearParks(Coordinate coordinate) {
		List<Park> allParksInDb = parkDao.getAll();
		List<Park> nearestParks = new ArrayList<Park>();
		
		// REVIEW use Haversine formula to calculate distance between each park and user location. Better idea?
		// REVIEW how about Google Places API for java?
		for(Park park : allParksInDb){
			Coordinate parkCoordinate = park.getCoordinate();
			double distance = Haversine.getDistance(parkCoordinate.getLatitude(), parkCoordinate.getLongitude(), 
													coordinate.getLatitude(), coordinate.getLongitude());
			
			if(distance <= NEAREST_PARK_RADIUS){
				nearestParks.add(park);
			}
		}
		
		return nearestParks;
	}

}
