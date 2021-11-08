package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import customservice.CustomUserService;
import dto.User;

/**
 * This class contains REST Endpoints for authenticated users.
 * @author sushi22
 *
 */

@RestController
@RequestMapping("authApi/v1")
public class UserController {
	
	@Autowired
	private CustomUserService userService;
	
	@GetMapping("/user/{userId}")
	private User getUser(@PathVariable String userId) {
		
		return userService.getUserById(userId);
		
	}
	
	@GetMapping("/users")
	private List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@DeleteMapping("/user/{userId}")
	private void deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		
	}
}
