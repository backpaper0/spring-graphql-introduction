package com.example.base.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.base.entity.Todo;
import com.example.base.entity.TodoStatus;
import com.example.base.repository.TodoRepository;

@Controller
public class TodoController {

	private final TodoRepository todoRepository;

	public TodoController(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@QueryMapping
	public List<Todo> allTodo() {
		return todoRepository.findAll(Sort.by(Order.asc("id")));
	}

	@QueryMapping
	public Optional<Todo> findTodo(@Argument Integer id) {
		return todoRepository.findById(id);
	}

	@MutationMapping
	public Todo createTodo(@Argument CreateTodo input) {
		Todo todo = new Todo();
		todo.setTitle(input.getTitle());
		todo.setTodoStatus(TodoStatus.TODO);
		return todoRepository.save(todo);
	}
}
