package com.example.base.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	@Enumerated(EnumType.STRING)
	private TodoStatus todoStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TodoStatus getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(TodoStatus todoStatus) {
		this.todoStatus = todoStatus;
	}
}
