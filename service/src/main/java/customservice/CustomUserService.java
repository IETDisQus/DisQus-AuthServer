package customservice;

import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import dto.User;
import dto.UserDTO;

public interface CustomUserService {
	
	public List<User> getAllUsers();
	
	public User getUserById(String userId);
	
	public void deleteUser(String userId);
	
	UserDTO processUserAuthentication(Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
	
}
