package com.example.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.base.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
