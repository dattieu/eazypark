package prototype.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDto {

	@NotNull
    @NotEmpty
    private String email;
	
    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
    
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMatchingPassword() {
		return matchingPassword;
	}
	
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
}
