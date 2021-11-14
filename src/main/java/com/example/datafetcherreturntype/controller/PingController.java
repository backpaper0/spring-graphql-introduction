package com.example.datafetcherreturntype.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PingController {

	@QueryMapping
	public Ping ping(@Argument boolean notNull, @Argument int size) {
		return new Ping(notNull, size);
	}

	@SchemaMapping
	public String ping1(Ping ping) {
		// 任意の型
		return ping.notNull ? "pong" : null;
	}

	@SchemaMapping
	public List<String> ping2(Ping ping) {
		// List
		return Collections.nCopies(ping.size, "pong");
	}

	@SchemaMapping
	public Optional<String> ping3(Ping ping) {
		// Optional
		return Optional.of("pong").filter(a -> ping.notNull);
	}

	@SchemaMapping
	public Stream<String> ping4(Ping ping) {
		// Stream
		return Collections.nCopies(ping.size, "pong").stream();
	}

	@SchemaMapping
	public Mono<String> ping5(Ping ping) {
		// Mono
		return Mono.just("pong").filter(a -> ping.notNull);
	}

	@SchemaMapping
	public Flux<String> ping6(Ping ping) {
		//Flux
		return Flux.fromIterable(Collections.nCopies(ping.size, "pong"));
	}

	@SchemaMapping
	public CompletionStage<String> ping7(Ping ping) {
		// CompletionStage
		return CompletableFuture.completedStage(ping.notNull ? "pong" : null);
	}

	@SchemaMapping
	public DataFetcherResult<String> ping8(Ping ping) {
		// graphql.execution.DataFetcherResult
		return DataFetcherResult.<String> newResult().data(ping.notNull ? "pong" : null).build();
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
