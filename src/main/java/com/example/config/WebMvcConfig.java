package com.example.config;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Configuration;
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
		// npm startでGraphiQLを動かした時にクエリーを実行できるようCORSの設定をする
		registry.addMapping(graphQlProperties.getPath())
				.allowCredentials(true)
				.allowedHeaders("*")
				.allowedMethods("*")
				.allowedOrigins("http://localhost:3000");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 利便性のためにわざわざ /index.html まで書かなくても良いようにしておく
		String path = "/my-graphiql";
		registry.addViewController(path).setViewName(path + "/index.html");
		registry.addViewController(path + "/").setViewName(path + "/index.html");
	}
}
