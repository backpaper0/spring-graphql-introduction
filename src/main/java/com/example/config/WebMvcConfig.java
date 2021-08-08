package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.boot.GraphQlProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final GraphQlProperties graphQlProperties;

	public WebMvcConfig(GraphQlProperties graphQlProperties) {
		this.graphQlProperties = graphQlProperties;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(graphQlProperties.getPath())
				.allowCredentials(true)
				.allowedHeaders("*")
				.allowedMethods("*")
				.allowedOrigins("http://localhost:3000");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String path = "/my-graphiql";
		registry.addViewController(path).setViewName(path + "/index.html");
		registry.addViewController(path + "/").setViewName(path + "/index.html");
	}
}
