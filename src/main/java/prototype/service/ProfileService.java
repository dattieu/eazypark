package prototype.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import prototype.model.Profile;

public interface ProfileService extends GenericService<Profile, Integer> {
	
	void changePhoto(int profileId, String fileName, MultipartFile photo) throws IOException;
	
}
