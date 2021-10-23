package com.example.base;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.example.base.entity.TodoStatus;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoTest {

	@Autowired
	private GraphQlTester graphQlTester;

	private Integer id1;
	private Integer id2;

	@Test
	@Order(100)
	void createTodo() {
		String query = "mutation CreateTodo($title: String!) {" +
				"  createTodo(input: {" +
				"    title: $title" +
				"  }) {" +
				"    id" +
				"    title" +
				"    todoStatus" +
				"  }" +
				"}";

		id1 = graphQlTester.query(query)
				.variable("title", "foo").execute()
				.path("createTodo.title").entity(String.class).isEqualTo("foo")
				.path("createTodo.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO)
				.path("createTodo.id").entity(Integer.class).get();

		id2 = graphQlTester.query(query)
				.variable("title", "bar").execute()
				.path("createTodo.title").entity(String.class).isEqualTo("bar")
				.path("createTodo.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO)
				.path("createTodo.id").entity(Integer.class).get();
	}
}
