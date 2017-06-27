package prototype.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import prototype.model.FileModel;

@Component
public class ImageFileValidator implements Validator {
	
	public static final String JPG_MIME_TYPE = "image/jpg";
	public static final long TEN_MB_IN_BYTES = 10485760;

    public boolean supports(Class<?> clazz) {
        return FileModel.class.isAssignableFrom(clazz);
    }
 
    public void validate(Object target, Errors errors) {
        FileModel fileUploadModel = (FileModel) target;
        MultipartFile file = fileUploadModel.getFile();
        
        if(file.isEmpty()) {
            errors.rejectValue("file", "upload.file.required");
        }
        
        if(!JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType())) {
            errors.rejectValue("file", "upload.invalid.file.type");
        }
        
        if(file.getSize() > TEN_MB_IN_BYTES) {
            errors.rejectValue("file", "upload.exceeded.file.size");
        }   
    }
 
}
