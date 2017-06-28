package prototype.service.impl;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import prototype.dao.GenericDao;
import prototype.dao.VehicleDao;
import prototype.model.Vehicle;
import prototype.service.VehicleService;

@Service("vehicleService")
public class VehicleServiceImpl extends GenericServiceImpl<Vehicle, String> implements VehicleService {

    private final VehicleDao vehicleDao;

    @Autowired
    public VehicleServiceImpl(@Autowired GenericDao<Vehicle, String> genericDao) {
    	super(genericDao);
    	this.vehicleDao = (VehicleDao) genericDao;
    }
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void register(Vehicle vehicle) {
		String registeringPlateNumber = vehicle.getPlateNumber();
		Vehicle dbVehicle = vehicleDao.getByKey("plateNumber", registeringPlateNumber);
	
		if(dbVehicle != null){
			throw new EntityExistsException("Vehicle with plate number " + registeringPlateNumber + " already registered");
		}
		vehicleDao.save(vehicle);
	}

}
