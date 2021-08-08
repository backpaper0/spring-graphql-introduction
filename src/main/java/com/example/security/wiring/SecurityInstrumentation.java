package com.example.security.wiring;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetcher;

@Component
public class SecurityInstrumentation extends SimpleInstrumentation {

	private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	@Override
	public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher,
			InstrumentationFieldFetchParameters parameters) {
		return env -> {

			if (env.getFieldDefinition().getDirective("authenticated") != null) {
				SecurityContext securityContext = SecurityContextHolder.getContext();
				Authentication authentication = securityContext.getAuthentication();
				if (authentication.isAuthenticated() == false
						|| authenticationTrustResolver.isAnonymous(authentication)) {
					throw new AuthenticationCredentialsNotFoundException("User is not Authenticated");
				}
			}

			return dataFetcher.get(env);
		};
	}
}
