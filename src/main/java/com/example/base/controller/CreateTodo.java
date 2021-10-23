package com.example.base.controller;

import com.example.base.entity.TodoTitle;

public class CreateTodo {

	private TodoTitle title;

	public CreateTodo() {
		System.out.println("xxx");
	}

	public TodoTitle getTitle() {
		return title;
	}

	public void setTitle(TodoTitle title) {
		this.title = title;
	}
}
