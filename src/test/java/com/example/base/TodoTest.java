package com.example.base;

import java.util.List;
import java.util.Map;

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

	private static final String ALL_TODO_QUERY = "query AllTodo {" +
			"  result: allTodo {" +
			"    id" +
			"    title" +
			"    todoStatus" +
			"  }" +
			"}";

	private static final String FIND_TODO_QUERY = "query FindTodo($id: ID!) {" +
			"  result: findTodo(id: $id) {" +
			"    id" +
			"    title" +
			"    todoStatus" +
			"  }" +
			"}";

	private static final String CREATE_TODO_QUERY = "mutation CreateTodo($title: TodoTitle!) {" +
			"  result: createTodo(input: {" +
			"    title: $title" +
			"  }) {" +
			"    id" +
			"    title" +
			"    todoStatus" +
			"  }" +
			"}";

	private static final String UPDATE_TODO_QUERY = "mutation UpdateTodo($input: UpdateTodo!) {" +
			"  result: updateTodo(input: $input) {" +
			"    id" +
			"    title" +
			"    todoStatus" +
			"  }" +
			"}";

	private static final String DELETE_TODO_QUERY = "mutation DeleteTodo($id: ID!) {" +
			"  result: deleteTodo(id: $id)" +
			"}";

	@Autowired
	private GraphQlTester graphQlTester;

	private Integer id1;
	private Integer id2;

	@Test
	@Order(100)
	void createTodo() {
		id1 = graphQlTester.query(CREATE_TODO_QUERY)
				.variable("title", "foo").execute()
				.path("result.title").entity(String.class).isEqualTo("foo")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO)
				.path("result.id").entity(Integer.class).get();

		id2 = graphQlTester.query(CREATE_TODO_QUERY)
				.variable("title", "bar").execute()
				.path("result.title").entity(String.class).isEqualTo("bar")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO)
				.path("result.id").entity(Integer.class).get();
	}

	@Test
	@Order(200)
	void allTodo() {
		graphQlTester.query(ALL_TODO_QUERY)
				.execute()
				.path("result[*].id").entityList(Integer.class).isEqualTo(List.of(id1, id2))
				.path("result[*].title").entityList(String.class).isEqualTo(List.of("foo", "bar"))
				.path("result[*].todoStatus").entityList(TodoStatus.class)
				.isEqualTo(List.of(TodoStatus.TODO, TodoStatus.TODO));
	}

	@Test
	@Order(200)
	void findTodo() {
		graphQlTester.query(FIND_TODO_QUERY)
				.variable("id", id1).execute()
				.path("result.id").entity(Integer.class).isEqualTo(id1)
				.path("result.title").entity(String.class).isEqualTo("foo")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO);

		graphQlTester.query(FIND_TODO_QUERY)
				.variable("id", id2).execute()
				.path("result.id").entity(Integer.class).isEqualTo(id2)
				.path("result.title").entity(String.class).isEqualTo("bar")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.TODO);
	}

	@Test
	@Order(300)
	void updateTodo() {
		Map<String, Object> input = Map.of(
				"id", id1,
				"title", "fooooooooo",
				"todoStatus", TodoStatus.DOING);

		graphQlTester.query(UPDATE_TODO_QUERY)
				.variable("input", input).execute()
				.path("result.id").entity(Integer.class).isEqualTo(id1)
				.path("result.title").entity(String.class).isEqualTo("fooooooooo")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.DOING);

		graphQlTester.query(FIND_TODO_QUERY)
				.variable("id", id1).execute()
				.path("result.id").entity(Integer.class).isEqualTo(id1)
				.path("result.title").entity(String.class).isEqualTo("fooooooooo")
				.path("result.todoStatus").entity(TodoStatus.class).isEqualTo(TodoStatus.DOING);
	}

	@Test
	@Order(300)
	void deleteTodo() {
		graphQlTester.query(DELETE_TODO_QUERY)
				.variable("id", id2).execute()
				.path("result").entity(Integer.class).isEqualTo(id2);

		graphQlTester.query(FIND_TODO_QUERY)
				.variable("id", id2).execute()
				.path("result").valueDoesNotExist();
	}
}
