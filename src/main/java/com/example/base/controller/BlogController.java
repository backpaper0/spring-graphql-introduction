package com.example.base.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.base.entity.Article;
import com.example.base.entity.Category;
import com.example.base.repository.ArticleRepository;
import com.example.base.repository.CategoryRepository;

@Controller
public class BlogController {

	private final ArticleRepository articleRepository;
	private final CategoryRepository categoryRepository;

	public BlogController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
		this.articleRepository = articleRepository;
		this.categoryRepository = categoryRepository;
	}

	@QueryMapping
	public Optional<Article> article(@Argument Integer id) {
		return articleRepository.findById(id);
	}

	@QueryMapping
	public List<Article> articles() {
		return articleRepository.findAll();
	}

	@QueryMapping
	public List<Category> categories() {
		return categoryRepository.findAll();
	}

	@SchemaMapping
	public Category category(Article article) {
		return article.getCategory();
	}

	@MutationMapping
	public Category createCategory(@Argument String name) {
		Category category = new Category();
		category.setName(name);
		return categoryRepository.save(category);
	}

	@MutationMapping
	public Optional<Article> createArticle(@Argument String title, @Argument String content,
			@Argument Integer categoryId) {
		return categoryRepository.findById(categoryId).map(category -> {
			Article article = new Article();
			article.setTitle(title);
			article.setContent(content);
			article.setCategory(category);
			return articleRepository.save(article);
		});
	}
}
