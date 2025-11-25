package com.jobSerachWebsite.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jobSerachWebsite.Dao.CustomUserDetailsService;
import com.jobSerachWebsite.Service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	public CustomSuccessHandler successH;
	@Autowired
	public CustomOAuth2UserService customOAuth2UserService;
	@Autowired
	public CustomFailureHandler failureH;
	@Bean
	public UserDetailsService getUserDetails() {
		CustomUserDetailsService customUserDetails=new CustomUserDetailsService();
		return customUserDetails;
	}
	
	@Bean
	
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuth=new DaoAuthenticationProvider();
		daoAuth.setUserDetailsService(getUserDetails());
		daoAuth.setPasswordEncoder(passwordEncoder());
		return daoAuth;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		System.out.println("securityfilterchain");
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth.requestMatchers("/user/**")
				.hasRole("USER")
				.requestMatchers("/recruiter/**")
				.hasRole("RECRUITER")
				.requestMatchers("/file/**","/images/**")
				.permitAll()
				.anyRequest()
				.permitAll())
		.formLogin(login->login
				.loginPage("/userlogin")
				.loginProcessingUrl("/login")
				.failureHandler(failureH)
				.successHandler(successH)
				.permitAll())
		.oauth2Login(oauth -> oauth
			    .loginPage("/userlogin")
			    .userInfoEndpoint(info -> info.userService(customOAuth2UserService))
			
			.successHandler(successH)  )  // ADD THIS

		.logout(logout->logout.logoutUrl("/logout")
				.logoutSuccessUrl("/jobs")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll());
		return http.build();
	}
	
	

}
