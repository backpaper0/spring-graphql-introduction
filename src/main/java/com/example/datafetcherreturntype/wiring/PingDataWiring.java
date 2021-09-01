package com.example.datafetcherreturntype.wiring;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PingDataWiring implements RuntimeWiringConfigurer {

	@Override
	public void configure(RuntimeWiring.Builder paramBuilder) {
		paramBuilder.type("Query", b -> b.dataFetcher("ping", env -> {
			boolean notNull = env.getArgument("notNull");
			int size = env.getArgument("size");
			return new Ping(notNull, size);
		}));

		paramBuilder.type("Ping", b -> b

				// 任意の型
				.dataFetcher("ping1", single(notNull -> {
					return notNull ? "pong" : null;
				}))

				// List
				.dataFetcher("ping2", multiple(size -> {
					return Collections.nCopies(size, "pong");
				}))

				// Optional
				.dataFetcher("ping3", single(notNull -> {
					return Optional.of("pong").filter(a -> notNull);
				}))

				// Stream
				.dataFetcher("ping4", multiple(size -> {
					return Collections.nCopies(size, "pong").stream();
				}))

				// Mono
				.dataFetcher("ping5", single(notNull -> {
					return Mono.just("pong").filter(a -> notNull);
				}))

				//Flux
				.dataFetcher("ping6", multiple(size -> {
					return Flux.fromIterable(Collections.nCopies(size, "pong"));
				}))

				// CompletionStage
				.dataFetcher("ping7", single(notNull -> {
					return CompletableFuture.completedStage(notNull ? "pong" : null);
				}))

				// graphql.execution.DataFetcherResult
				.dataFetcher("ping8", single(notNull -> {
					return DataFetcherResult.newResult().data(notNull ? "pong" : null).build();
				})));
	}

	private static <T> DataFetcher<T> single(Function<Boolean, T> f) {
		return env -> {
			Ping ping = env.getSource();
			return f.apply(ping.notNull);
		};
	}

	private static <T> DataFetcher<T> multiple(IntFunction<T> f) {
		return env -> {
			Ping ping = env.getSource();
			return f.apply(ping.size);
		};
	}

	private static class Ping {

		boolean notNull;
		int size;

		public Ping(boolean notNull, int size) {
			this.notNull = notNull;
			this.size = size;
		}
	}
}
