package prototype.dao.impl;

import org.springframework.stereotype.Repository;

import prototype.dao.ParkDao;
import prototype.model.Park;

@Repository("parkDao")
public class ParkDaoImpl extends GenericDaoImpl<Park, String> implements ParkDao {

}
