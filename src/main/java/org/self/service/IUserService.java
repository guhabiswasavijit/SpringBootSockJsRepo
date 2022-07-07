package org.self.service;

import org.self.model.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface IUserService {
	User findByUserName(String username);
	User createNewUser(User user);
	boolean isUsernameExist(String username);
	UserDetails loadUserByUsername(String username);
	void save(User user);

}
