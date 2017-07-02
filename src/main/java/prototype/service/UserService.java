package prototype.service;

import prototype.model.User;

public interface UserService extends GenericService<User, String> {
	
	boolean login(User user);
	void register(User user);
	void changePassword(String newPassword);
	
}
