package com.example.querybyexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class TweetTest {

	@Autowired
	private GraphQlTester graphQlTester;

	private final String tweetsQuery = """
			query Tweets($username: String!) {
			  tweets(username: $username) {
			    id
			  }
			}
			""";

	private final String tweetQuery = """
			query Tweets($id: ID!) {
			  tweet(id: $id) {
			    id
			    username
			    content
			  }
			}
			""";

	@Test
	void tweets() {
		graphQlTester.document(tweetsQuery)
				.variable("username", "foo")
				.execute()

				.path("tweets[*].id")
				.entityList(Integer.class)
				.contains(1, 3, 4);
	}

	@Test
	void tweet() {
		graphQlTester.document(tweetQuery)
				.variable("id", 5)
				.execute()

				.path("tweet.id").entity(Integer.class).isEqualTo(5)
				.path("tweet.username").entity(String.class).isEqualTo("bar")
				.path("tweet.content").entity(String.class).isEqualTo("5000兆円欲しい！");
	}
}
