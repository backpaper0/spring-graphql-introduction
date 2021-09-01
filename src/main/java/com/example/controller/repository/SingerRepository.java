package com.example.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.controller.entity.Singer;

public interface SingerRepository extends JpaRepository<Singer, Integer> {
}
