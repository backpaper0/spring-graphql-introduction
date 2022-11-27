package com.example.base;

import java.util.List;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;

@Component
public class ConstraintViolationExceptionHandler extends DataFetcherExceptionResolverAdapter {

	@Override
	protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
		if (ex instanceof ConstraintViolationException) {

			ConstraintViolationException e = (ConstraintViolationException) ex;
			return e.getConstraintViolations().stream()
					.map(cv -> GraphqlErrorBuilder
							.newError(env)
							.message(cv.getMessage())
							.build())
					.toList();
		}
		return null;
	}
}
