package com.example.errorhandling.wiring;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import graphql.GraphQLContext;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Component
public class MetaVarExceptionResolver extends DataFetcherExceptionResolverAdapter {

	@Override
	protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
		GraphQLContext context = env.getGraphQlContext();
		if (context != null) {
			Boolean handling = context.get("handling");

			if (Boolean.TRUE.equals(handling)) {
				GraphQLError error = GraphqlErrorBuilder.newError(env)
						.message("Handled: %s", ex.getMessage()).build();
				return error;
			}
		}
		return null;
	}
}
