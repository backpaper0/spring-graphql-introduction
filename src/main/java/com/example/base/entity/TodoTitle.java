package com.example.base.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TodoTitle {

	private final String value;

	@JsonCreator
	public TodoTitle(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
