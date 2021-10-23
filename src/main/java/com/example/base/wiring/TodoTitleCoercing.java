package com.example.base.wiring;

import com.example.base.entity.TodoTitle;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class TodoTitleCoercing implements Coercing<TodoTitle, String> {

	@Override
	public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
		if (dataFetcherResult instanceof TodoTitle) {
			return ((TodoTitle) dataFetcherResult).getValue();
		}
		throw new CoercingSerializeException();
	}

	@Override
	public TodoTitle parseValue(Object input) throws CoercingParseValueException {
		if (input instanceof String) {
			return new TodoTitle((String) input);
		}
		throw new CoercingParseValueException();
	}

	@Override
	public TodoTitle parseLiteral(Object input) throws CoercingParseLiteralException {
		if (input instanceof StringValue) {
			return new TodoTitle(((StringValue) input).getValue());
		}
		throw new CoercingParseLiteralException();
	}
}
