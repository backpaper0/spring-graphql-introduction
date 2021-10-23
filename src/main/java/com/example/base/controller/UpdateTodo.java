package com.example.base.controller;

import com.example.base.entity.TodoStatus;
import com.example.base.entity.TodoTitle;

public class UpdateTodo {

	private Integer id;
	private TodoTitle title;
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
