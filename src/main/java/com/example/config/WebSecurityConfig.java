package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorizeRequests -> authorizeRequests
						// GraphQLエンドポイントはpermitAllにしておいて他の仕組みで認可制御を行う
						.antMatchers("/graphql").permitAll()
						.antMatchers("/graphiql/**").permitAll()
						.antMatchers("/actuator/**").permitAll()
						.anyRequest().authenticated())

				.httpBasic().and()

				.csrf(csrf -> csrf.ignoringAntMatchers("/graphql"))

				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	}
}
