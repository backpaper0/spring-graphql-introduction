package com.example.subscription.wiring;

import java.time.Duration;

import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import graphql.schema.idl.RuntimeWiring;
import reactor.core.publisher.Flux;

@Component
public class CountDataWiring implements RuntimeWiringBuilderCustomizer {

	@Override
	public void customize(RuntimeWiring.Builder paramBuilder) {
		paramBuilder.type("Subscription", b -> b.dataFetcher("count", env -> {
			int size = env.getArgument("size");
			return Flux.interval(Duration.ofSeconds(1)).skip(1).take(size);
		}));
	}
}
