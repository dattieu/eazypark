package prototype.service;

import prototype.dto.PasswordChangeDto;
import prototype.model.User;

public interface UserService extends GenericService<User, String> {
	
	boolean login(User user);
	void register(User user);
	void changePassword(PasswordChangeDto pwdChangeDto);
	
}
