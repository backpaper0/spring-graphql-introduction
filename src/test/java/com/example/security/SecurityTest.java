package com.example.security;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class SecurityTest {

	@Autowired
	private GraphQlTester graphQlTester;

	private static final String PUBLIC_QUERY = "{"
			+ "  security {"
			+ "    public"
			+ "  }"
			+ "}";

	private static final String PROTECTED_QUERY = "{"
			+ "  security {"
			+ "    protected"
			+ "  }"
			+ "}";

	@Test
	void publicNotAuthenticated() throws Exception {
		graphQlTester.query(PUBLIC_QUERY)
				.execute()

				.path("security.public")
				.entity(String.class)
				.isEqualTo("PUBLIC: anonymousUser");
	}

	@Test
	@WithMockUser(username = "foobar")
	void publicAuthenticated() throws Exception {
		graphQlTester.query(PUBLIC_QUERY)
				.execute()

				.path("security.public")
				.entity(String.class)
				.isEqualTo("PUBLIC: foobar");
	}

	@Test
	void protectedNotAuthenticated() throws Exception {
		graphQlTester.query(PROTECTED_QUERY)
				.execute()

				.errors()
				.filter(error -> error.getPath().equals(List.of("security", "protected"))
						&& error.getErrorType().equals(ErrorType.UNAUTHORIZED))
				.verify();
	}

	@Test
	@WithMockUser(username = "foobar")
	void protectedAuthenticated() throws Exception {
		graphQlTester.query(PROTECTED_QUERY)
				.execute()

				.path("security.protected")
				.entity(String.class)
				.isEqualTo("PROTECTED: foobar");
	}
}
