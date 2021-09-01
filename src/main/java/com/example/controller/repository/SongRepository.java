package com.example.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.controller.entity.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
}
