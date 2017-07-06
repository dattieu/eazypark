package prototype.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class PasswordChangeDto {

	@Email
	@NotBlank
	private String userEmail;
	
	@NotBlank
	private String currentPassword;
	
	@NotBlank
	private String newPassword;
    
	@NotBlank
	private String matchingNewPassword;
	
	public PasswordChangeDto() {}

	public PasswordChangeDto(String userEmail, String currentPassword, String newPassword, String matchingNewPassword) {
		super();
		this.userEmail = userEmail;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.matchingNewPassword = matchingNewPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMatchingNewPassword() {
		return matchingNewPassword;
	}

	public void setMatchingNewPassword(String matchingNewPassword) {
		this.matchingNewPassword = matchingNewPassword;
	}
	
}
