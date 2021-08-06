package com.example.dataloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dataloader.entity.Comic;

public interface ComicRepository extends JpaRepository<Comic, Integer> {
}
