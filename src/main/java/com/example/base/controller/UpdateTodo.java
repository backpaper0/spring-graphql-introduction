package com.example.base.controller;

import org.springframework.data.web.ProjectedPayload;

import com.example.base.entity.TodoStatus;
import com.example.base.entity.TodoTitle;

@ProjectedPayload
public interface UpdateTodo {

	Integer getId();

	TodoTitle getTitle();

	TodoStatus getTodoStatus();
}
