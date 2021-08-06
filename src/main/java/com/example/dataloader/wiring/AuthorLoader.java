package com.example.dataloader.wiring;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.dataloader.BatchLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.dataloader.entity.Author;
import com.example.dataloader.repository.AuthorRepository;

@Component
public class AuthorLoader implements BatchLoader<Integer, Author> {

	private static final Logger logger = LoggerFactory.getLogger(AuthorLoader.class);

	private final AuthorRepository authorRepository;

	public AuthorLoader(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public CompletionStage<List<Author>> load(List<Integer> keys) {
		logger.debug("AuthorLoader.load: keys = {}", keys);
		List<Author> authors = authorRepository.findAllById(keys);
		return CompletableFuture.completedStage(authors);
	}
}
