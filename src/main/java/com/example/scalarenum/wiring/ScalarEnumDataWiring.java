package com.example.scalarenum.wiring;

import java.net.URI;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import com.example.scalarenum.entity.GitHubRepository;
import com.example.scalarenum.entity.Visibility;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.EnumValuesProvider;
import graphql.schema.idl.NaturalEnumValuesProvider;
import graphql.schema.idl.RuntimeWiring;

@Component
public class ScalarEnumDataWiring implements RuntimeWiringConfigurer {

	@Override
	public void configure(RuntimeWiring.Builder paramBuilder) {

		Coercing<?, ?> coercing = new URICoercing();
		GraphQLScalarType scalarType = GraphQLScalarType.newScalar().name("URI").coercing(coercing).build();
		paramBuilder.scalar(scalarType);

		EnumValuesProvider enumValuesProvider = new NaturalEnumValuesProvider<>(Visibility.class);
		paramBuilder.type("Visibility", b -> b.enumValues(enumValuesProvider));

		paramBuilder.type("Query", b -> b.dataFetcher("ghRepo", env -> {
			URI uri = URI.create("https://github.com/backpaper0/spring-graphql-introduction");
			Visibility visibility = Visibility.PUBLIC;
			return new GitHubRepository(uri, visibility);
		}));
	}
}
