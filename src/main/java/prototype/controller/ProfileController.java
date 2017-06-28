package prototype.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import prototype.model.FileModel;
import prototype.service.ProfileService;
import prototype.validator.ImageFileValidator;

@RestController
public class ProfileController {
	
	private static final String PHOTO_CHANGE = "profiles/{userId}";

	private final ProfileService profileService;
	private final ImageFileValidator imageFileValidator;
	
	@Autowired
	public ProfileController(ProfileService profileService, ImageFileValidator imageFileValidator) {
		this.profileService = profileService;
		this.imageFileValidator = imageFileValidator;
	}
	
	@InitBinder("photoModel")
	protected void bindMultiPartFileUpload(WebDataBinder binder) {
        binder.setValidator(imageFileValidator);
    } 
	
	// REVIEW because user and profile are primary key shared, profileId = userId
	// REVIEW how to test this e.g, with Postman?
	@PostMapping(PHOTO_CHANGE)
	public void changeUserPhoto(@PathVariable(value = "userId") int profileId, 
			@Valid FileModel photoModel, BindingResult result) throws IOException, FileUploadException {
		
		if(result.hasErrors()) {
			throw new FileUploadException("Error uploading file");
		}
		profileService.changePhoto(profileId, photoModel.getFile());
	}
	
}
