package com.example.dataloader.wiring;

import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import com.example.dataloader.entity.Author;
import com.example.dataloader.entity.Comic;
import com.example.dataloader.repository.AuthorRepository;
import com.example.dataloader.repository.ComicRepository;

import graphql.schema.idl.RuntimeWiring;

@Component
public class ComicDataWiring implements RuntimeWiringConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(ComicDataWiring.class);

	private final ComicRepository comicRepository;
	private final AuthorRepository authorRepository;

	public ComicDataWiring(ComicRepository comicRepository, AuthorRepository authorRepository) {
		this.comicRepository = comicRepository;
		this.authorRepository = authorRepository;
	}

	@Override
	public void configure(RuntimeWiring.Builder paramBuilder) {
		paramBuilder.type("Query", b -> b.dataFetcher("comics", env -> {
			logger.debug("Fetch Query.comics");
			return comicRepository.findAll();
		}));

		paramBuilder.type("Comic", b -> b.dataFetcher("author", env -> {
			boolean useDataLoader = env.getArgument("useDataLoader");
			Comic source = env.getSource();
			logger.debug("Fetch Comic.author: authorId = {}", source.getAuthorId());
			if (useDataLoader) {
				DataLoader<Integer, Author> authorLoader = env.getDataLoader("authorLoader");
				return authorLoader.load(source.getAuthorId());
			}
			return authorRepository.findById(source.getAuthorId());
		}));
	}
}
