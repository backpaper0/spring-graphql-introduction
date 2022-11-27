package com.example.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private TodoTitle title;
	@Enumerated(EnumType.STRING)
	private TodoStatus todoStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TodoTitle getTitle() {
		return title;
	}

	public void setTitle(TodoTitle title) {
		this.title = title;
	}

	public TodoStatus getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(TodoStatus todoStatus) {
		this.todoStatus = todoStatus;
	}
}
