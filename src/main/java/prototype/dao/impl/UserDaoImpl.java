package prototype.dao.impl;

import org.springframework.stereotype.Repository;

import prototype.dao.UserDao;
import prototype.model.User;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, String> implements UserDao {

}
