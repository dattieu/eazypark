package prototype.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import prototype.exception.GlobalExceptionHandler;
import prototype.model.FileModel;
import prototype.service.ProfileService;
import prototype.validator.PhotoValidator;

@RestController
public class ProfileController {
	
	private static final String PHOTO_CHANGE = "profiles/photos/{userId}";
	
	protected final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

	private final ProfileService profileService;
	private final PhotoValidator photoValidator;
	
	@Autowired
	public ProfileController(ProfileService profileService, PhotoValidator photoValidator) {
		this.profileService = profileService;
		this.photoValidator = photoValidator;
	}
	
	@PostMapping(PHOTO_CHANGE)
	public void changeUserPhoto(@PathVariable(value = "userId") int profileId, 
			@Valid @ModelAttribute("photo") FileModel photoFileModel, BindingResult result) throws IOException, FileUploadException {	
		
		photoValidator.validate(photoFileModel, result);
		if(result.hasErrors()) {
			throw new FileUploadException("Error uploading file");
		}
		profileService.changePhoto(profileId, photoFileModel.getFile());
	}
	
}
