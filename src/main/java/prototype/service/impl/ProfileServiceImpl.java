package prototype.service.impl;

import java.io.File;
import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import prototype.dao.GenericDao;
import prototype.dao.ProfileDao;
import prototype.model.Profile;
import prototype.service.ProfileService;
import prototype.util.FileHandler;

@Service("profileService")
public class ProfileServiceImpl extends GenericServiceImpl<Profile, Integer> implements ProfileService {
	
	private final ProfileDao profileDao;

	@Autowired
    public ProfileServiceImpl(@Autowired GenericDao<Profile, Integer> genericDao) {
		super(genericDao);
        this.profileDao = (ProfileDao) genericDao;
    }

	@Transactional(propagation = Propagation.REQUIRED)
	public void changePhoto(int profileId, String fileName, MultipartFile photo) throws IOException {
		
		Profile profile = profileDao.getByKey("id", profileId);
		if(profile == null) {
			throw new EntityNotFoundException("Profile of user with id " + profileId + " not found");
		}
		
		// TODO set directory path for photo files
		profile.setPhoto(fileName);
		profileDao.update(profile);
		
		// TODO save photo into file system, create a basic image file system?
		File folderDir = FileHandler.createDirectory("");
		File imageFile = FileHandler.createFile(folderDir, fileName);
		FileHandler.writeFileAsBytes(photo, imageFile);	
	}
	
}
