package com.example.base.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
	public Todo createTodo(@Valid @Argument CreateTodo input) {
		Todo todo = new Todo();
		todo.setTitle(input.getTitle());
		todo.setTodoStatus(TodoStatus.TODO);
		return todoRepository.save(todo);
	}

	@MutationMapping
	public Todo updateTodo(@Argument UpdateTodo input) {
		Todo todo = todoRepository.findById(input.getId()).orElseThrow();
		if (input.getTitle() != null) {
			todo.setTitle(input.getTitle());
		}
		if (input.getTodoStatus() != null) {
			todo.setTodoStatus(input.getTodoStatus());
		}
		return todoRepository.save(todo);
	}

	@MutationMapping
	public Integer deleteTodo(@Argument Integer id) {
		todoRepository.deleteById(id);
		return id;
	}
}
