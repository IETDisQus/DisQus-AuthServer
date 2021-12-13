package controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.User;

@RestController
public class AuthController {
	
	@GetMapping("/")
	public String welcome() {
		return("Welcome!!");
	}
	
	//Get currently logged-in user
	@GetMapping("/currentuser")
	public User user(@AuthenticationPrincipal User principal) {
        return principal;
    }
}
