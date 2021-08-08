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
		http.authorizeRequests()
				.antMatchers("/graphql/**").permitAll()
				.antMatchers("/my-graphiql/**").permitAll()
				.anyRequest().authenticated()

				.and().httpBasic()

				.and().csrf().disable()

				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
