package com.example.errorhandling.controller;

import java.util.List;
import java.util.Set;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.errorhandling.exception.MetaVarException;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;

@Controller
public class MetaVarController {

	@QueryMapping
	public Object errorhandling(DataFetchingEnvironment env) {
		GraphQLContext context = env.getGraphQlContext();
		boolean handling = env.getArgument("handling");
		context.put("handling", handling);
		String exception = env.getArgument("exception");
		context.put("exception", exception);
		if (exception.equals("ROOT")) {
			throw new MetaVarException();
		}
		return List.of("foo", "bar", "baz", "qux");
	}

	@SchemaMapping(typeName = "MetaVar")
	public Object name(DataFetchingEnvironment env) {
		String name = env.getSource();
		GraphQLContext context = env.getGraphQlContext();
		String exception = context.get("exception");
		if (exception.equals("METAVAR") && Set.of("bar", "baz").contains(name)) {
			throw new MetaVarException("Exception occurred while processing " + name);
		}
		return name;
	}
}
