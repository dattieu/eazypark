package prototype.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.io.IOUtils;
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
		Profile profile = profileDao.getById(profileId);
		if(profile == null) {
			throw new EntityNotFoundException("Profile " + profileId + " not found");
		}
		
		// REVIEW Path only works with Java 7 or higher
		// REVIEW need a basic image file system server? UUID for file name?
		String photoFileName = Integer.toString(profileId).concat(photo.getOriginalFilename());
		Path photoFilePathInDb = Paths.get(environment.getRequiredProperty("image.upload.location"), photoFileName);
		Files.copy(photo.getInputStream(), photoFilePathInDb, StandardCopyOption.REPLACE_EXISTING);
		
		profile.setPhoto(photoFileName);
		profileDao.update(profile);	
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public byte[] getPhoto(int profileId) throws IOException {
		Profile profile = profileDao.getById(profileId);
		if(profile == null) {
			throw new EntityNotFoundException("Profile " + profileId + " not found");
		}
		
		String photoFileAbsolutePath = Paths.get(environment.getRequiredProperty("image.upload.location"), profile.getPhoto()).toString();
		InputStream in = new FileInputStream(photoFileAbsolutePath);
		return IOUtils.toByteArray(in);
	}
	
}
