package com.jobSerachWebsite.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/files/**")
					.addResourceLocations("file:///C:/Users/Ghanashyam Majhi/Uploads/");
		registry.addResourceHandler("/images/**").addResourceLocations("file:///C:/Users/Ghanashyam Majhi/Userimages/");
	}
	

}
