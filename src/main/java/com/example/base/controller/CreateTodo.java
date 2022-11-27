package com.example.base.controller;

import com.example.base.entity.TodoTitle;

import jakarta.validation.Valid;

public class CreateTodo {

	@Valid
	private TodoTitle title;

	public TodoTitle getTitle() {
		return title;
	}

	public void setTitle(TodoTitle title) {
		this.title = title;
	}
}
