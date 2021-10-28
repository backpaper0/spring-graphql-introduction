package com.example.dataloader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dataloader.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select a from Author a where a.id in :ids")
	List<Author> findByIds(List<Integer> ids);
}
