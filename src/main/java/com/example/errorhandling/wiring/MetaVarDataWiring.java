package com.example.errorhandling.wiring;

import java.util.List;
import java.util.Set;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import graphql.GraphQLContext;
import graphql.schema.idl.RuntimeWiring;

@Component
public class MetaVarDataWiring implements RuntimeWiringConfigurer {

	@Override
	public void configure(RuntimeWiring.Builder paramBuilder) {
		paramBuilder.type("Query", b -> b.dataFetcher("errorhandling", env -> {
			GraphQLContext context = env.getContext();
			boolean handling = env.getArgument("handling");
			context.put("handling", handling);
			String exception = env.getArgument("exception");
			context.put("exception", exception);
			if (exception.equals("ROOT")) {
				throw new Exception();
			}
			return List.of("foo", "bar", "baz", "qux");
		}));

		paramBuilder.type("MetaVar", b -> b.dataFetcher("name", env -> {
			String name = env.getSource();
			GraphQLContext context = env.getContext();
			String exception = context.get("exception");
			if (exception.equals("METAVAR") && Set.of("bar", "baz").contains(name)) {
				throw new Exception("Exception occurred while processing " + name);
			}
			return name;
		}));
	}
}
