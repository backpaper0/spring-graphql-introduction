package com.example.dataloader.controller;

import java.util.List;

import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.dataloader.entity.Author;
import com.example.dataloader.entity.Comic;
import com.example.dataloader.repository.AuthorRepository;
import com.example.dataloader.repository.ComicRepository;

import graphql.schema.DataFetchingEnvironment;

@Controller
public class ComicController {

	private static final Logger logger = LoggerFactory.getLogger(ComicController.class);

	private final ComicRepository comicRepository;
	private final AuthorRepository authorRepository;

	public ComicController(ComicRepository comicRepository, AuthorRepository authorRepository) {
		this.comicRepository = comicRepository;
		this.authorRepository = authorRepository;
	}

	@QueryMapping
	public List<Comic> comics() {
		logger.debug("Fetch Query.comics");
		return comicRepository.findAll();
	}

	@SchemaMapping
	public Object author(@Argument boolean useDataLoader, Comic source, DataFetchingEnvironment env) {
		logger.debug("Fetch Comic.author: authorId = {}", source.getAuthorId());
		if (useDataLoader) {
			DataLoader<Integer, Author> authorLoader = env.getDataLoader("authorLoader");
			return authorLoader.load(source.getAuthorId());
		}
		return authorRepository.findById(source.getAuthorId());
	}
}
