package utils;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider {
	
	 public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
	    public static final String SIGNING_KEY = "disqus354756";

	public String createToken(Authentication authentication) {
		UserDTO userPrincipal = (UserDTO) authentication.getPrincipal();

		Date expiryDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000);

		return Jwts.builder().setSubject((userPrincipal.getUser().getUserId())).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
	}

}
