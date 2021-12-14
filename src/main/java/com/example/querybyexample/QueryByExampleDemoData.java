package com.example.querybyexample;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.querybyexample.entity.Tweet;
import com.example.querybyexample.repository.TweetRepository;

@Component
public class QueryByExampleDemoData implements ApplicationRunner {

	private final TweetRepository repos;

	public QueryByExampleDemoData(TweetRepository repos) {
		this.repos = repos;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		repos.save(new Tweet(1, "foo", "やあ"));
		repos.save(new Tweet(2, "bar", "どうも"));
		repos.save(new Tweet(3, "foo", "ふむふむ"));
		repos.save(new Tweet(4, "foo", "なるほど"));
		repos.save(new Tweet(5, "bar", "5000兆円欲しい！"));
	}
}
