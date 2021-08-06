package com.example.paging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paging.entity.Commit;

public interface CommitRepository extends JpaRepository<Commit, Integer> {
}
