package com.example.base.wiring;

import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import com.example.base.entity.Article;
import com.example.base.entity.Category;
import com.example.base.repository.ArticleRepository;
import com.example.base.repository.CategoryRepository;

import graphql.schema.idl.RuntimeWiring;

@Component
public class BlogDataWiring implements RuntimeWiringBuilderCustomizer {

	private final ArticleRepository articleRepository;
	private final CategoryRepository categoryRepository;

	public BlogDataWiring(ArticleRepository articleRepository,
			CategoryRepository categoryRepository) {
		this.articleRepository = articleRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void customize(RuntimeWiring.Builder paramBuilder) {
		paramBuilder.type("Query", b -> b.dataFetcher("article", env -> {
			Integer id = Integer.valueOf(env.getArgument("id"));
			return articleRepository.findById(id);
		}).dataFetcher("categories", env -> {
			return categoryRepository.findAll();
		}).dataFetcher("articles", env -> {
			return articleRepository.findAll();
		}));

		paramBuilder.type("Article", b -> b.dataFetcher("category", env -> {
			Article article = env.getSource();
			return article.getCategory();
		}));

		paramBuilder.type("Mutation", b -> b.dataFetcher("createCategory", env -> {
			String name = env.getArgument("name");
			Category category = new Category();
			category.setName(name);
			return categoryRepository.save(category);
		}).dataFetcher("createArticle", env -> {
			String title = env.getArgument("title");
			String content = env.getArgument("content");
			Integer categoryId = Integer.valueOf(env.getArgument("categoryId"));
			return categoryRepository.findById(categoryId).map(category -> {
				Article article = new Article();
				article.setTitle(title);
				article.setContent(content);
				article.setCategory(category);
				return articleRepository.save(article);
			});
		}));
	}
}
