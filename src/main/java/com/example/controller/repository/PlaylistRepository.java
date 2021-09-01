package com.example.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.controller.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
}
