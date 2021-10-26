package customservice;

import java.util.List;

import domain.User;

public interface CustomUserService {
	
	public List<User> getAllUsers();
	
	public User getUserById(String userId);
	
	public void deleteUser(String userId);
	
}
