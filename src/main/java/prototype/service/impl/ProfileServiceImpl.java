package prototype.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import prototype.dao.GenericDao;
import prototype.dao.ProfileDao;
import prototype.model.Profile;
import prototype.service.ProfileService;

@Service("profileService")
@PropertySource(value = { "classpath:database.properties" })
public class ProfileServiceImpl extends GenericServiceImpl<Profile, Integer> implements ProfileService {
	
	private final ProfileDao profileDao;
	
	private final Environment environment;

	@Autowired
	public ProfileServiceImpl(@Autowired GenericDao<Profile, Integer> genericDao, Environment environment) {
		super(genericDao);
		this.profileDao = (ProfileDao) genericDao;
		this.environment = environment;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void changePhoto(int profileId, MultipartFile photo) throws IOException {
		
		Profile profile = profileDao.getByKey("id", profileId);
		if(profile == null) {
			throw new EntityNotFoundException("Profile of user with id " + profileId + " not found");
		}
		
		// REVIEW Path only works with Java 7 or higher
		// REVIEW need a basic image file system server? UUID for file name?
		String photoFileName = Integer.toString(profileId).concat(photo.getOriginalFilename());
		Path photoFile = Paths.get(environment.getRequiredProperty("image.upload.location"), photoFileName);
		Files.copy(photo.getInputStream(), photoFile);
		
		profile.setPhoto(photoFileName);
		profileDao.update(profile);	
	}
	
}
