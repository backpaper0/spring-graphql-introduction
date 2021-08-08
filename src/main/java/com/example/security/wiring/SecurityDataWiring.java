package com.example.security.wiring;

import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import com.example.security.service.SecurityDemoService;

import graphql.schema.idl.RuntimeWiring;

@Component
public class SecurityDataWiring implements RuntimeWiringBuilderCustomizer {

	private final SecurityDemoService service;

	public SecurityDataWiring(SecurityDemoService service) {
		this.service = service;
	}

	@Override
	public void customize(RuntimeWiring.Builder builder) {
		builder.type("Query", b -> b.dataFetcher("security", env -> new Object()));
		builder.type("SecurityDemo", b -> b
				.dataFetcher("public", env -> service.doPublic())
				.dataFetcher("protected", env -> service.doProtected()));
	}
}
