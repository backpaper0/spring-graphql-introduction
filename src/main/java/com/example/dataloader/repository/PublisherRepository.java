package com.example.dataloader.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dataloader.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

	@Query("select a from Publisher a where a.id = :id")
	Optional<Publisher> myFindById(Integer id);
}
