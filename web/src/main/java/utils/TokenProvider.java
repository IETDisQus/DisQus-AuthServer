package utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	
	 public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
	    public static final String SIGNING_KEY = "disqus354756";

	public String createToken(Authentication authentication) {
		UserDTO userPrincipal = (UserDTO) authentication.getPrincipal();

		Date expiryDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000);

		return Jwts.builder().setSubject((userPrincipal.getUser().getUserId())).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
	}
	
	public String getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
