package prototype.dao.impl;

import org.springframework.stereotype.Repository;

import prototype.dao.VehicleDao;
import prototype.model.Vehicle;

@Repository("vehicletDao")
public class VehicleDaoImpl extends GenericDaoImpl<Vehicle, String> implements VehicleDao {

}
