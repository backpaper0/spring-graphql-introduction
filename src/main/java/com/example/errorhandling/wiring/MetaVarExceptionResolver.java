package com.example.errorhandling.wiring;

import java.util.List;

import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.stereotype.Component;

import graphql.GraphQLContext;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Component
public class MetaVarExceptionResolver implements DataFetcherExceptionResolver {

	@Override
	public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {
		GraphQLContext context = environment.getGraphQlContext();
		if (context != null) {
			Boolean handling = context.get("handling");

			if (Boolean.TRUE.equals(handling)) {
				GraphQLError error = GraphqlErrorBuilder.newError(environment)
						.message("Handled: %s", exception.getMessage()).build();
				List<GraphQLError> errors = List.of(error);
				return Mono.just(errors);
			}
		}
		return Mono.empty();
	}
}
