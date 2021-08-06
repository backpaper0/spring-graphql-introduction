package com.example.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.base.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
