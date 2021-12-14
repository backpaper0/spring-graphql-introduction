package com.example.base.controller;

import javax.validation.Valid;

import com.example.base.entity.TodoTitle;

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
