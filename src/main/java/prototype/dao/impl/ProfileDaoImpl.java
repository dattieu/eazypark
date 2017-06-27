package prototype.dao.impl;

import org.springframework.stereotype.Repository;

import prototype.dao.ProfileDao;
import prototype.model.Profile;

@Repository("profileDao")
public class ProfileDaoImpl extends GenericDaoImpl<Profile, Integer> implements ProfileDao {
	
}
