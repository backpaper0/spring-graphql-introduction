package com.example.dataloader.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.dataloader.entity.Author;
import com.example.dataloader.entity.Comic;
import com.example.dataloader.entity.Publisher;
import com.example.dataloader.repository.AuthorRepository;
import com.example.dataloader.repository.ComicRepository;
import com.example.dataloader.repository.PublisherRepository;

import graphql.schema.DataFetchingEnvironment;

@Controller
public class ComicController {

	private static final Logger logger = LoggerFactory.getLogger(ComicController.class);

	private final ComicRepository comicRepository;
	private final AuthorRepository authorRepository;
	private final PublisherRepository publisherRepository;

	public ComicController(ComicRepository comicRepository, AuthorRepository authorRepository,
			PublisherRepository publisherRepository) {
		this.comicRepository = comicRepository;
		this.authorRepository = authorRepository;
		this.publisherRepository = publisherRepository;
	}

	@QueryMapping
	public List<Comic> comics() {
		logger.debug("Fetch Query.comics");
		return comicRepository.findAll();
	}

	@BatchMapping
	public List<Author> author(List<Comic> sources) {
		List<Integer> ids = sources.stream().map(Comic::getAuthorId).toList();
		logger.debug("Fetch Comic.author: authorIds = {}", ids);
		Map<Integer, Author> authors = authorRepository.findByIds(ids).stream()
				.collect(Collectors.toMap(Author::getId, Function.identity()));
		return ids.stream().map(authors::get).toList();
	}

	@SchemaMapping
	public Optional<Publisher> publisher(Comic source, DataFetchingEnvironment env) {
		logger.debug("Fetch Comic.publisher: publisherId = {}", source.getPublisherId());
		// findByIdだとJPAがキャッシュしてくれちゃうので自前でメソッドを用意。
		// キャッシュしてくれるのはありがたいけれど、DataLoaderを見せたい今この場面ではキャッシュしたくない。
		return publisherRepository.myFindById(source.getPublisherId());
	}
}
