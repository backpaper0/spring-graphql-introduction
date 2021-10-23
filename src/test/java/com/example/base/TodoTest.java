package com.example.base;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
@TestInstance(Lifecycle.PER_CLASS)
public class TodoTest {

	@Autowired
	private GraphQlTester graphQlTester;

	private Integer id1;
	private Integer id2;

	@Test
	@Order(100)
	void createTodo() {
		String query = "mutation CreateTodo($title: String!) {" +
				"  result: createTodo(input: {" +
				"    title: $title" +
				"  }) {" +
				"    id" +
				"    title" +
				"    todoStatus" +
				"  }" +
				"}";

		id1 = graphQlTester.query(query)
				.variable("title", "foo").execute()
				.path("result.title").entity(String.class).isEqualTo("foo")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO)
				.path("result.id").entity(Integer.class).get();

		id2 = graphQlTester.query(query)
				.variable("title", "bar").execute()
				.path("result.title").entity(String.class).isEqualTo("bar")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO)
				.path("result.id").entity(Integer.class).get();
	}

	@Test
	@Order(200)
	void allTodo() {
		String query = "query AllTodo {" +
				"  result: allTodo {" +
				"    id" +
				"    title" +
				"    todoStatus" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.execute()
				.path("result[*].id").entityList(Integer.class).isEqualTo(List.of(id1, id2))
				.path("result[*].title").entityList(String.class).isEqualTo(List.of("foo", "bar"))
				.path("result[*].todoStatus").entityList(TodoStatus.class)
				.isEqualTo(List.of(TodoStatus.TODO, TodoStatus.TODO));
	}

	@Test
	@Order(200)
	void findTodo() {
		String query = "query FindTodo($id: ID!) {" +
				"  result: findTodo(id: $id) {" +
				"    id" +
				"    title" +
				"    todoStatus" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.variable("id", id1).execute()
				.path("result.id").entity(Integer.class).isEqualTo(id1)
				.path("result.title").entity(String.class).isEqualTo("foo")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO);

		graphQlTester.query(query)
				.variable("id", id2).execute()
				.path("result.id").entity(Integer.class).isEqualTo(id2)
				.path("result.title").entity(String.class).isEqualTo("bar")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO);
	}
}
