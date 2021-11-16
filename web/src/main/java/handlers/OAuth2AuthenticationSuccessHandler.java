package handlers;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import config.AppConfig;
import config.HttpCookieOAuth2AuthorizationRequestRepository;
import exceptions.BadRequestException;
import utils.CookieProvider;
import utils.TokenProvider;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Autowired
	OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, AppConfig appConfig,
			HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		this.tokenProvider = tokenProvider;
		this.appConfig= appConfig;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);
		
		System.out.println("@@@@@@@@@@@ onAuthenticationSuccess @@@@@@@@@@@@@@");
		
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& targetUrl: "+targetUrl+" &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Optional<String> redirectUri = CookieProvider.getCookie(request, "redirect_uri").map(Cookie::getValue);
		
		System.out.println("@@@@@@@@@@@  determineTargetUrl @@@@@@@@@@@@@@");

		
		System.out.println("$$$$$$ "+ redirectUri.get() + " $$$$$$" );

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
		
		System.out.println("########## targetUrl: "+targetUrl+" ###########");

		String token = tokenProvider.createToken(authentication);

		return UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		
		System.out.println("@@@@@@@@@@@ isAuthorizedRedirectUri  @@@@@@@@@@@@@@");

		
		URI clientRedirectUri = URI.create(uri);

		return appConfig.getAuthorizedRedirectUris().stream().anyMatch(authorizedRedirectUri -> {
			// Only validate host and port. Let the clients use different paths if they want
			// to
			URI authorizedURI = URI.create(authorizedRedirectUri);
			if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) && authorizedURI.getPort() == clientRedirectUri.getPort()) {
				return true;
			}
			return false;
		});
	}

}
