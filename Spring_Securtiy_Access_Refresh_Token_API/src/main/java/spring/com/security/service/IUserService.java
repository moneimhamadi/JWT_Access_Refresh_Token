package spring.com.security.service;

import java.util.List;

import spring.com.security.entities.User;

public interface IUserService {

	public User addUser(User user);
	public User getOneUserByUsername(String username);
	public List<User> getAllUsers();
	public String enableUser(String emailUser);
	public int isEnabled(String username);
	public int sendEmailForgetPassword( String username);
	public int changePassword(String token,String newPassword);
}
