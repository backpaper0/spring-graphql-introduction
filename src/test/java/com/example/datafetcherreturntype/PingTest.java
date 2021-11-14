package com.example.datafetcherreturntype;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class PingTest {

	@Autowired
	private GraphQlTester graphQlTester;

	private static final String QUERY = "query Ping($notNull: Boolean, $size: Int){"
			+ "  ping(notNull: $notNull, size: $size) {"
			+ "    ping1"
			+ "    ping2"
			+ "    ping3"
			+ "    ping4"
			+ "    ping5"
			+ "    ping6"
			+ "    ping7"
			+ "    ping8"
			+ "  }"
			+ "}";

	@Test
	void defaultParameter() {
		graphQlTester.query(QUERY)
				.execute()
				.path("ping.ping1").entity(String.class).isEqualTo("pong")
				.path("ping.ping2").entityList(String.class).isEqualTo(List.of("pong", "pong", "pong"))
				.path("ping.ping3").entity(String.class).isEqualTo("pong")
				.path("ping.ping4").entityList(String.class).isEqualTo(List.of("pong", "pong", "pong"))
				.path("ping.ping5").entity(String.class).isEqualTo("pong")
				.path("ping.ping6").entityList(String.class).isEqualTo(List.of("pong", "pong", "pong"))
				.path("ping.ping7").entity(String.class).isEqualTo("pong")
				.path("ping.ping8").entity(String.class).isEqualTo("pong");
	}

	@Test
	void nullAndSize0() {
		graphQlTester.query(QUERY)
				.variable("notNull", false)
				.variable("size", 0)
				.execute()
				.path("ping.ping1").valueIsEmpty()
				.path("ping.ping2").valueIsEmpty()
				.path("ping.ping3").valueIsEmpty()
				.path("ping.ping4").valueIsEmpty()
				.path("ping.ping5").valueIsEmpty()
				.path("ping.ping6").valueIsEmpty()
				.path("ping.ping7").valueIsEmpty()
				.path("ping.ping8").valueIsEmpty();
	}
}
