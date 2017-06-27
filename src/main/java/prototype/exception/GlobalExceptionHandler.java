package prototype.exception;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import prototype.constant.Constant;

// TODO more meaningful and efficient way to handle exception?
@ControllerAdvice
public class GlobalExceptionHandler {
	
	protected final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = Constant.INVALID_CREDENTIALS)
	@ExceptionHandler(UsernameNotFoundException.class)
	public void handleLoginError(UsernameNotFoundException exception) {
		logger.error(exception);
	}
	
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason = Constant.FILE_UPLOAD_ERROR)
	@ExceptionHandler(FileUploadException.class)
	public void handleFileUploadError(FileUploadException exception) {
		logger.error(exception);
	}
	
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = Constant.UNABLE_TO_REGISTER)
	@ExceptionHandler(EntityExistsException.class)
	public void handleUnableToRegister(EntityExistsException exception) {
		logger.error(exception);
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = Constant.RESOURCE_NOT_EXIST)
	@ExceptionHandler(EntityNotFoundException.class)
	public void handleUnableToFindResources(EntityNotFoundException exception) {
		logger.error(exception);
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = Constant.ILLEGAL_REQUEST_PARAMS)
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalRequestParams(IllegalArgumentException exception) {
		logger.error(exception);
	}

}
