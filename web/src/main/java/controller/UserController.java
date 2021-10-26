package controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import domain.User;

/**
 * This class contains REST Endpoints for authenticated users.
 * @author sushi22
 *
 */

@RestController
public class UserController {
	
	@GetMapping("/user/{userId}")
	private User getUser(@PathVariable String userId) {
		
		//call user service;
		return null;
		
	}
	
	@GetMapping("/users")
	private List<User> getAllUsers(){
		return null;
	}
	
	@DeleteMapping("/user/{userId}")
	private void deleteUser() {
		
	}
}
