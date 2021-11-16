package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import handlers.OAuth2AuthenticationFailureHandler;
import handlers.OAuth2AuthenticationSuccessHandler;
import openIdservice.MyOidcUserService;
import utils.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyOidcUserService oidcUserService;
	
	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	

    @Bean
    public JwtTokenFilter tokenAuthenticationFilter() {
        return new JwtTokenFilter();
    }
	
	@Override
	protected  void configure(HttpSecurity http) throws Exception {
		
		http
		.cors()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf()
        .disable()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and()
		.authorizeRequests()
			.antMatchers("/", "/error","/oauth2/**").permitAll()
		.anyRequest()
			.authenticated()
			.and()
		.oauth2Login()
			.authorizationEndpoint()
			.baseUri("/oauth2/authorize")
				.authorizationRequestRepository(cookieAuthorizationRequestRepository())
				.and()
			.redirectionEndpoint()
			.baseUri("/login/oauth2/code/google")
				.and()
			.userInfoEndpoint()
				.oidcUserService(oidcUserService)
				.and()
			.successHandler(oAuth2AuthenticationSuccessHandler)
			.failureHandler(oAuth2AuthenticationFailureHandler);
		
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}
	
}
