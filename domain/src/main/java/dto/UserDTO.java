package dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class UserDTO implements OidcUser{
	private static final long serialVersionUID = -2845160792248762779L;
	private OidcIdToken idToken;
	private OidcUserInfo userInfo;
	private Map<String, Object> attributes;
	private User user;
	
	
	public UserDTO(User user,Map<String,Object> attributes,OidcIdToken idToken, OidcUserInfo userInfo) {
		
		this.attributes = attributes;
		this.user = user;
		this.idToken = idToken;
		this.userInfo = userInfo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.attributes.get("authorities");
	}
	
	@Override
	public String getName() {
		return this.user.getName();
	}
	
	@Override
	public Map<String, Object> getClaims() {
		return this.attributes;
	}
	
	@Override
	public OidcUserInfo getUserInfo() {
				return this.userInfo;
	}
	
	@Override
	public OidcIdToken getIdToken() {
				return this.idToken;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}
	
	public User getUser() {
		return this.user;
	}

}
