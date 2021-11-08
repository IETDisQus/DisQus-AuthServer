package openIdservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import customservice.CustomUserService;
import exceptions.OAuth2AuthenticationProcessingException;

@Service
public class MyOidcUserService extends OidcUserService {
	
	@Autowired
	private CustomUserService userService;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);
		try {
			return userService.processUserAuthentication(oidcUser.getAttributes(), oidcUser.getIdToken(),
					oidcUser.getUserInfo());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new OAuth2AuthenticationProcessingException(ex.getMessage(), ex.getCause());
		}
	}

}
