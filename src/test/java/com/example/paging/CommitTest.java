package com.example.paging;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
@ActiveProfiles("test")
public class CommitTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Value("${commit-test.queries.forward}")
	private String forwardQuery;

	@Value("${commit-test.queries.backward}")
	private String backwardQuery;

	@ParameterizedTest
	@CsvSource(value = {
			"null |  1 | 10 | false | true ",
			"   0 |  1 | 10 | false | true ",
			"  10 | 11 | 20 | true  | true ",
			"  20 | 21 | 30 | true  | true ",
			"  30 | 31 | 40 | true  | true ",
			"  40 | 41 | 50 | true  | false",
			"  39 | 40 | 49 | true  | true ",
			"  41 | 42 | 50 | true  | false",
	}, delimiter = '|', nullValues = "null")
	void forward(String after, int start, int end, boolean hasPreviousPage, boolean hasNextPage) {
		graphQlTester.document(forwardQuery)
				.variable("after", after)
				.execute()

				.path("history.forward.edges[*].cursor")
				.entityList(String.class)
				.isEqualTo(IntStream.rangeClosed(start, end)
						.mapToObj(Integer::toString)
						.collect(Collectors.toList()))

				.path("history.forward.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(hasPreviousPage)

				.path("history.forward.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(hasNextPage)

				.path("history.forward.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo(Integer.toString(start))

				.path("history.forward.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo(Integer.toString(end));
	}

	@Test
	void forwardEmpty() {
		graphQlTester.document(forwardQuery)
				.variable("after", "9999")
				.execute()

				.path("history.forward.edges")
				.entityList(Object.class).isEqualTo(List.of())

				.path("history.forward.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("history.forward.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("history.forward.pageInfo.startCursor")
				.valueIsNull()

				.path("history.forward.pageInfo.endCursor")
				.valueIsNull();
	}

	@ParameterizedTest
	@CsvSource(value = {
			"null | 41 | 50 | true  | false",
			"  51 | 41 | 50 | true  | false",
			"  41 | 31 | 40 | true  | true ",
			"  31 | 21 | 30 | true  | true ",
			"  21 | 11 | 20 | true  | true ",
			"  11 |  1 | 10 | false | true ",
			"  12 |  2 | 11 | true  | true ",
			"  10 |  1 |  9 | false | true ",
	}, delimiter = '|', nullValues = "null")
	void backward(String before, int start, int end, boolean hasPreviousPage, boolean hasNextPage) {
		graphQlTester.document(backwardQuery)
				.variable("before", before)
				.execute()

				.path("history.backward.edges[*].cursor")
				.entityList(String.class)
				.isEqualTo(IntStream.rangeClosed(start, end)
						.mapToObj(Integer::toString)
						.collect(Collectors.toList()))

				.path("history.backward.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(hasPreviousPage)

				.path("history.backward.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(hasNextPage)

				.path("history.backward.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo(Integer.toString(start))

				.path("history.backward.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo(Integer.toString(end));
	}

	@Test
	void backwardEmpty() {
		graphQlTester.document(backwardQuery)
				.variable("before", "0")
				.execute()

				.path("history.backward.edges")
				.entityList(Object.class).isEqualTo(List.of())

				.path("history.backward.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("history.backward.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("history.backward.pageInfo.startCursor")
				.valueIsNull()

				.path("history.backward.pageInfo.endCursor")
				.valueIsNull();
	}
}
