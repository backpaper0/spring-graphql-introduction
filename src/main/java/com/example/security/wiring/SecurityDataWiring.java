package com.example.security.wiring;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import com.example.security.service.SecurityDemoService;

import graphql.schema.idl.RuntimeWiring;

@Component
public class SecurityDataWiring implements RuntimeWiringConfigurer {

	private final SecurityDemoService service;

	public SecurityDataWiring(SecurityDemoService service) {
		this.service = service;
	}

	@Override
	public void configure(RuntimeWiring.Builder builder) {
		builder.type("Query", b -> b.dataFetcher("security", env -> new Object()));
		builder.type("SecurityDemo", b -> b
				.dataFetcher("public", env -> service.doPublic())
				.dataFetcher("protected", env -> service.doProtected())
				.dataFetcher("roleFoo", env -> service.roleFoo())
				.dataFetcher("roleBarBaz", env -> service.roleBarBaz())
				.dataFetcher("protected2", env -> service.protected2()));
	}
}
