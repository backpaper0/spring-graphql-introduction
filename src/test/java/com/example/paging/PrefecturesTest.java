package com.example.paging;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class PrefecturesTest {

	@Autowired
	private GraphQlTester graphQlTester;

	private final String query = "query Prefectures($first: Int, $after: String, $last: Int, $before: String) {"
			+ "  prefectures(first: $first, after: $after, last: $last, before: $before) {"
			+ "    edges {"
			+ "      node"
			+ "      cursor"
			+ "    }"
			+ "    pageInfo {"
			+ "      hasPreviousPage"
			+ "      hasNextPage"
			+ "      startCursor"
			+ "      endCursor"
			+ "    }"
			+ "  }"
			+ "}";

	@Test
	void all() {
		graphQlTester.query(query)
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.hasSize(47)

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjA=")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjQ2");
	}

	@Test
	void first() {
		graphQlTester.query(query)
				.variable("first", 3)
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.isEqualTo(List.of("愛知県", "青森県", "秋田県"))

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjA=")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjI=");
	}

	@Test
	void before() {
		graphQlTester.query(query)
				.variable("before", "c2ltcGxlLWN1cnNvcjM=")
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.isEqualTo(List.of("愛知県", "青森県", "秋田県"))

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjA=")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjI=");
	}

	@Test
	void firstAfter() {
		graphQlTester.query(query)
				.variable("first", 4)
				.variable("after", "c2ltcGxlLWN1cnNvcjI=")
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.isEqualTo(List.of("石川県", "茨城県", "岩手県", "愛媛県"))

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjM=")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjY=");
	}

	@Test
	void last() {
		graphQlTester.query(query)
				.variable("last", 3)
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.isEqualTo(List.of("山口県", "山梨県", "和歌山県"))

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjQ0")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjQ2");
	}

	@Test
	void after() {
		graphQlTester.query(query)
				.variable("after", "c2ltcGxlLWN1cnNvcjQz")
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.isEqualTo(List.of("山口県", "山梨県", "和歌山県"))

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjQ0")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjQ2");
	}

	@Test
	void lastBefore() {
		graphQlTester.query(query)
				.variable("last", 4)
				.variable("before", "c2ltcGxlLWN1cnNvcjQ0")
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.isEqualTo(List.of("三重県", "宮城県", "宮崎県", "山形県"))

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjQw")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjQz");
	}

	@Test
	void firstAfterLastBefore() {
		graphQlTester.query(query)
				.variable("first", 10)
				.variable("after", "c2ltcGxlLWN1cnNvcjE5")
				.variable("last", 10)
				.variable("before", "c2ltcGxlLWN1cnNvcjI3")
				.execute()

				.path("prefectures.edges[*].node")
				.entityList(String.class)
				.isEqualTo(List.of("佐賀県", "滋賀県", "静岡県", "島根県", "千葉県", "東京都", "徳島県"))

				.path("prefectures.pageInfo.hasPreviousPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("prefectures.pageInfo.startCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjIw")

				.path("prefectures.pageInfo.endCursor")
				.entity(String.class)
				.isEqualTo("c2ltcGxlLWN1cnNvcjI2");
	}
}
