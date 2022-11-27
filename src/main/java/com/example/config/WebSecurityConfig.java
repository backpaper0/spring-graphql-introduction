package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						// GraphQLエンドポイントはpermitAllにしておいて他の仕組みで認可制御を行う
						.requestMatchers("/graphql").permitAll()
						.requestMatchers("/graphiql/**").permitAll()
						.requestMatchers("/actuator/**").permitAll()
						.anyRequest().authenticated())

				.httpBasic().and()

				.csrf(csrf -> csrf.ignoringRequestMatchers("/graphql"))

				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}
}
