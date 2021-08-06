package com.example.dataloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dataloader.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
