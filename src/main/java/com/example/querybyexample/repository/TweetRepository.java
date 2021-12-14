package com.example.querybyexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.querybyexample.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, String> {
}
