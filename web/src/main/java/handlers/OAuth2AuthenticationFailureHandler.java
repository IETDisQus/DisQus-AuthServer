package handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import config.HttpCookieOAuth2AuthorizationRequestRepository;
import utils.CookieProvider;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	@Autowired
	HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String targetUrl = CookieProvider.getCookie(request, "redirect_uri").map(Cookie::getValue).orElse(("/"));

		targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("error", exception.getLocalizedMessage()).build().toUriString();

		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

}
