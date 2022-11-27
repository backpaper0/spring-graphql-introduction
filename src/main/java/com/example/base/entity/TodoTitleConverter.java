package com.example.base.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TodoTitleConverter implements AttributeConverter<TodoTitle, String> {

	@Override
	public String convertToDatabaseColumn(TodoTitle attribute) {
		if (attribute != null) {
			return attribute.getValue();
		}
		return null;
	}

	@Override
	public TodoTitle convertToEntityAttribute(String dbData) {
		if (dbData != null) {
			return new TodoTitle(dbData);
		}
		return null;
	}

}
