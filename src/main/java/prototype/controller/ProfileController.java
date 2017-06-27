package prototype.controller;

import java.io.IOException;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import prototype.service.ProfileService;

@RestController
public class ProfileController {

	private final ProfileService profileService;
	
	private static final String PHOTO_CHANGE = "profiles/{userId}";
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	// REVIEW because user and profile are primary key shared, profileId = userId?
	// REVIEW how to test this e.g, with Postman?
	// TODO validate multi-part file?
	@PostMapping(PHOTO_CHANGE)
	public void changeUserPhoto(@PathVariable(value = "userId") int profileId, 
			@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile photo) throws IOException, FileUploadException {
		
		if(photo.isEmpty()) {
			throw new FileUploadException("File " + fileName + " is empty");
		}
		profileService.changePhoto(profileId, fileName, photo);
	}
	
}
