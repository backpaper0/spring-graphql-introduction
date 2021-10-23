package com.example.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.base.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
