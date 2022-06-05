package com.example.base;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.base.entity.Article;
import com.example.base.entity.Category;
import com.example.base.repository.ArticleRepository;
import com.example.base.repository.CategoryRepository;

@Component
public class BaseDemoData implements ApplicationRunner {

	private final ArticleRepository articleRepository;
	private final CategoryRepository categoryRepository;

	public BaseDemoData(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
		this.articleRepository = articleRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		Category graphQL = categoryRepository.save(new Category("GraphQL"));
		Category git = categoryRepository.save(new Category("Git"));
		Category servlet = categoryRepository.save(new Category("Servlet"));
		Category oauth2 = categoryRepository.save(new Category("OAuth 2.0"));
		Category wildfly = categoryRepository.save(new Category("WildFly"));
		Category maven = categoryRepository.save(new Category("Maven"));
		Category github = categoryRepository.save(new Category("GitHub"));
		Category react = categoryRepository.save(new Category("React"));

		articleRepository.save(new Article("Spring GraphQL introduction", "...", graphQL));
		articleRepository.save(new Article("部分的にgit cloneしたりgit checkoutする", "...", git));
		articleRepository.save(new Article("ServletでSameSite Cookieを設定する", "...", servlet));
		articleRepository.save(new Article("セッションIDを生成するところ", "...", servlet));
		articleRepository.save(new Article("OAuth 2.0・OIDCを試すためにKeycloakへ入門する", "...", oauth2));
		articleRepository.save(new Article("DockerでWildFlyを試す", "...", wildfly));
		articleRepository.save(new Article("Mavenでたまに役立つ小ネタ", "...", maven));
		articleRepository.save(new Article("Jibで作ったコンテナイメージをGitHub Packagesへデプロイする", "...", github));
		articleRepository.save(new Article("GitHub PackagesへJARをデプロイする", "...", github));
		articleRepository.save(new Article("Create React Appで作ったReactアプリケーションを相対パスへデプロイする", "...", react));
	}
}
