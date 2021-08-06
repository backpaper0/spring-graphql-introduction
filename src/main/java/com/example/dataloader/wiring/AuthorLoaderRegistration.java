package com.example.dataloader.wiring;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.dataloader.DataLoaderRegistry;
import org.springframework.graphql.web.WebGraphQlHandler;
import org.springframework.graphql.web.WebInput;
import org.springframework.graphql.web.WebInterceptor;
import org.springframework.graphql.web.WebOutput;
import org.springframework.stereotype.Component;

import com.example.dataloader.entity.Author;

import reactor.core.publisher.Mono;

@Component
public class AuthorLoaderRegistration implements WebInterceptor {

	private final AuthorLoader authorLoader;

	public AuthorLoaderRegistration(AuthorLoader authorLoader) {
		this.authorLoader = authorLoader;
	}

	@Override
	public Mono<WebOutput> intercept(WebInput webInput, WebGraphQlHandler next) {

		webInput.configureExecutionInput((input, builder) -> {
			DataLoaderRegistry registry = new DataLoaderRegistry();
			if (input.getDataLoaderRegistry() != null) {
				registry = registry.combine(input.getDataLoaderRegistry());
			}

			DataLoaderOptions options = DataLoaderOptions.newOptions()
			//					.setMaxBatchSize(3)
			;
			DataLoader<Integer, Author> dataLoader = DataLoader.newDataLoader(authorLoader, options);
			registry.register("authorLoader", dataLoader);

			return builder.dataLoaderRegistry(registry).build();
		});

		return next.handle(webInput);
	}
}
