package com.example.base.wiring;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;

@Component
public class TodoTitleDataWiring implements RuntimeWiringConfigurer {

	@Override
	public void configure(RuntimeWiring.Builder paramBuilder) {
		Coercing<?, ?> coercing = new TodoTitleCoercing();
		GraphQLScalarType scalarType = GraphQLScalarType.newScalar().name("TodoTitle").coercing(coercing).build();
		paramBuilder.scalar(scalarType);
	}
}
