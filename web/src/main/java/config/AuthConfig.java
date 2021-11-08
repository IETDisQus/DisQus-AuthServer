package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import handlers.OAuth2AuthenticationFailureHandler;
import handlers.OAuth2AuthenticationSuccessHandler;
import openIdservice.MyOidcUserService;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyOidcUserService oidcUserService;
	
	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	
	@Override
	protected  void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
			.antMatchers("/", "/error","/oauth2/**").permitAll()
		.anyRequest()
			.authenticated()
			.and()
		.oauth2Login()
			.authorizationEndpoint()
				.authorizationRequestRepository(cookieAuthorizationRequestRepository())
				.and()
			.redirectionEndpoint()
				.and()
			.userInfoEndpoint()
				.oidcUserService(oidcUserService)
				.and()
			.successHandler(oAuth2AuthenticationSuccessHandler)
			.failureHandler(oAuth2AuthenticationFailureHandler);

	}

	
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}
	
	
}
