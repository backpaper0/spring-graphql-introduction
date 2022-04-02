package com.example.base;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class BlogTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void article() {
		String query = "{" +
				"  article(id: 1) {" +
				"    title" +
				"    category {" +
				"      name" +
				"    }" +
				"  }" +
				"}";
		//		String query = """
		//				{
		//				  article(id: 1) {
		//				    title
		//				    category {
		//				      name
		//				    }
		//				  }
		//				}
		//				""";

		graphQlTester.document(query)
				.execute()

				.path("article.title")
				.entity(String.class)
				.isEqualTo("Spring GraphQL introduction")

				.path("article.category.name")
				.entity(String.class)
				.isEqualTo("GraphQL");
	}
}
