package com.example.base.entity;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class TodoTitle {

	@Size(min = 1, message = "title length must be greater than or equal 1")
	private final String value;

	@JsonCreator
	public TodoTitle(String value) {
		if (value.length() > 10) {
			throw new IllegalArgumentException("title length must be less than or equal 10");
		}
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
