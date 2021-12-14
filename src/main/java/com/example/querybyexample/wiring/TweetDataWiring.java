package com.example.querybyexample.wiring;

import org.springframework.graphql.data.query.QueryByExampleDataFetcher;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import com.example.querybyexample.repository.TweetRepository;

import graphql.schema.idl.RuntimeWiring.Builder;

@Component
public class TweetDataWiring implements RuntimeWiringConfigurer {

	private final TweetRepository repos;

	public TweetDataWiring(TweetRepository repos) {
		this.repos = repos;
	}

	@Override
	public void configure(Builder builder) {
		builder.type("Query", b -> b
				.dataFetcher("tweets", QueryByExampleDataFetcher.builder(repos).many())
				.dataFetcher("tweet", QueryByExampleDataFetcher.builder(repos).single()));
	}
}
