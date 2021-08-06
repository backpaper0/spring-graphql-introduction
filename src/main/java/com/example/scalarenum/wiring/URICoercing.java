package com.example.scalarenum.wiring;

import java.net.URI;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class URICoercing implements Coercing<URI, String> {

	@Override
	public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
		if (dataFetcherResult instanceof URI) {
			return dataFetcherResult.toString();
		}
		throw new CoercingSerializeException();
	}

	@Override
	public URI parseValue(Object input) throws CoercingParseValueException {
		if (input instanceof String) {
			return URI.create((String) input);
		}
		throw new CoercingParseValueException();
	}

	@Override
	public URI parseLiteral(Object input) throws CoercingParseLiteralException {
		if (input instanceof StringValue) {
			return URI.create(((StringValue) input).getValue());
		}
		throw new CoercingParseLiteralException();
	}
}
