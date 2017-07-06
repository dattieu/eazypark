package prototype.controller;

import java.io.IOException;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadException;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.model.FileModel;
import prototype.service.ProfileService;
import prototype.validator.PhotoValidator;

@RestController
public class ProfileController {
	
	private static final String PROFILE_PHOTO = "profiles/photos/{userId}";

	private final ProfileService profileService;
	
	private final PhotoValidator photoValidator;
	
	// REVIEW use Spring Aspect here to add a id obfuscation layer?
	private final Hashids idObfuscator;
	
	@Autowired
	public ProfileController(ProfileService profileService, PhotoValidator photoValidator, Hashids idObfuscator) {
		this.profileService = profileService;
		this.photoValidator = photoValidator;
		this.idObfuscator = idObfuscator;
	}
	
	@PostMapping(PROFILE_PHOTO)
	public void changeProfilePhoto(@PathVariable(value = "userId") String profileId, 
			@Valid @ModelAttribute("photo") FileModel photoFileModel, BindingResult result) throws IOException, FileUploadException {	
		
		int deObfuscatedProfileId = (int) idObfuscator.decode(profileId)[0];
		photoValidator.validate(photoFileModel, result);
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_UPLOADING_FILE);
		}
		profileService.changePhoto(deObfuscatedProfileId, photoFileModel.getFile());
	}
	
	@GetMapping(PROFILE_PHOTO)
	//TODO need testing
	public byte[] getProfilePhoto(@PathVariable(value = "userId") String profileId) throws IOException {
		int deObfuscatedProfileId = (int) idObfuscator.decode(profileId)[0];
		return profileService.getPhoto(deObfuscatedProfileId);
	} 
	
}
