package com.example.security;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithAnonymousUser;
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

	private static final String ROLE_FOO_QUERY = "{"
			+ "  security {"
			+ "    roleFoo"
			+ "  }"
			+ "}";

	private static final String ROLE_BARBAZ_QUERY = "{"
			+ "  security {"
			+ "    roleBarBaz"
			+ "  }"
			+ "}";

	private static final String PROTECTED2_QUERY = "{"
			+ "  security {"
			+ "    protected2"
			+ "  }"
			+ "}";

	@Test
	void publicNotAuthenticated() {
		graphQlTester.document(PUBLIC_QUERY)
				.execute()

				.path("security.public")
				.entity(String.class)
				.isEqualTo("PUBLIC: <none>");
	}

	@Test
	@WithMockUser(username = "foobar")
	void publicAuthenticated() {
		graphQlTester.document(PUBLIC_QUERY)
				.execute()

				.path("security.public")
				.entity(String.class)
				.isEqualTo("PUBLIC: foobar");
	}

	@Test
	void protectedNotAuthenticated() {
		graphQlTester.document(PROTECTED_QUERY)
				.execute()

				.errors()
				.filter(error -> error.getParsedPath().equals(List.of("security", "protected"))
						&& error.getErrorType().equals(ErrorType.UNAUTHORIZED))
				.verify();
	}

	@Test
	@WithMockUser(username = "foobar")
	void protectedAuthenticated() {
		graphQlTester.document(PROTECTED_QUERY)
				.execute()

				.path("security.protected")
				.entity(String.class)
				.isEqualTo("PROTECTED: foobar");
	}

	@Test
	@WithMockUser(username = "demo", roles = { "FOO" })
	void roleFoo() {
		graphQlTester.document(ROLE_FOO_QUERY)
				.execute()

				.path("security.roleFoo")
				.entity(String.class)
				.isEqualTo("foo");
	}

	@Test
	@WithMockUser(username = "demo", roles = { "BAR", "BAZ" })
	void roleFoo2() {
		graphQlTester.document(ROLE_FOO_QUERY)
				.execute()

				.errors()
				.filter(error -> error.getParsedPath().equals(List.of("security", "roleFoo"))
						&& error.getErrorType().equals(ErrorType.UNAUTHORIZED))
				.verify();
	}

	@Test
	@WithMockUser(username = "demo", roles = { "BAR", "BAZ" })
	void roleBarBaz1() {
		graphQlTester.document(ROLE_BARBAZ_QUERY)
				.execute()

				.path("security.roleBarBaz")
				.entity(String.class)
				.isEqualTo("barbaz");
	}

	@Test
	@WithMockUser(username = "demo", roles = { "FOO", "BAR" })
	void roleBarBaz2() {
		graphQlTester.document(ROLE_BARBAZ_QUERY)
				.execute()

				.errors()
				.filter(error -> error.getParsedPath().equals(List.of("security", "roleBarBaz"))
						&& error.getErrorType().equals(ErrorType.UNAUTHORIZED))
				.verify();
	}

	@Test
	@WithMockUser(username = "demo", roles = { "BAZ", "QUX" })
	void roleBarBaz3() {
		graphQlTester.document(ROLE_BARBAZ_QUERY)
				.execute()

				.errors()
				.filter(error -> error.getParsedPath().equals(List.of("security", "roleBarBaz"))
						&& error.getErrorType().equals(ErrorType.UNAUTHORIZED))
				.verify();
	}

	@Test
	@WithMockUser(username = "demo")
	void protected2Authenticated() {
		graphQlTester.document(PROTECTED2_QUERY)
				.execute()
				.path("security.protected2")
				.entity(String.class)
				.isEqualTo("PROTECTED2: demo");
	}

	@Test
	void protected2NotAuthenticated() {
		graphQlTester.document(PROTECTED2_QUERY)
				.execute()

				.errors()
				.filter(error -> error.getParsedPath().equals(List.of("security", "protected2"))
						&& error.getErrorType().equals(ErrorType.INTERNAL_ERROR)) // UNAUTHORIZEDにしたい
				.verify();
	}

	@Test
	@WithAnonymousUser
	void protected2Anonymous() {
		graphQlTester.document(PROTECTED2_QUERY)
				.execute()

				.errors()
				.filter(error -> error.getParsedPath().equals(List.of("security", "protected2"))
						&& error.getErrorType().equals(ErrorType.UNAUTHORIZED)) // なんでこっちはUNAUTHORIZEDなんだろう？
				.verify();
	}
}
