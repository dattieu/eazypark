package prototype.controller;

import java.io.IOException;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.exception.GlobalExceptionHandler;
import prototype.model.FileModel;
import prototype.service.ProfileService;
import prototype.validator.PhotoValidator;

@RestController
public class ProfileController {
	
	private static final String PROFILE_PHOTO = "profiles/photos/{userId}";
	
	protected final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

	private final ProfileService profileService;
	private final PhotoValidator photoValidator;
	
	@Autowired
	public ProfileController(ProfileService profileService, PhotoValidator photoValidator) {
		this.profileService = profileService;
		this.photoValidator = photoValidator;
	}
	
	@PostMapping(PROFILE_PHOTO)
	public void changeProfilePhoto(@PathVariable(value = "userId") int profileId, 
			@Valid @ModelAttribute("photo") FileModel photoFileModel, BindingResult result) throws IOException, FileUploadException {	
		photoValidator.validate(photoFileModel, result);
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_UPLOADING_FILE);
		}
		profileService.changePhoto(profileId, photoFileModel.getFile());
	}
	
	@GetMapping(PROFILE_PHOTO)
	//TODO need testing
	public byte[] getProfilePhoto(@PathVariable(value = "userId") int profileId) throws IOException {
		return profileService.getPhoto(profileId);
	} 
	
}
