package com.example.subscription.controller;

import java.time.Duration;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class CountController {

	@SubscriptionMapping
	public Flux<Long> count(@Argument int size) {
		return Flux.interval(Duration.ofSeconds(1)).skip(1).take(size);
	}
}
