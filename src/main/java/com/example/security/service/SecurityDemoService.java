package com.example.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityDemoService {

	public String doPublic() {
		return "PUBLIC: " + name();
	}

	@PreAuthorize("isAuthenticated()")
	public String doProtected() {
		return "PROTECTED: " + name();
	}

	@Secured("ROLE_FOO")
	public String roleFoo() {
		return "foo";
	}

	@Secured({ "ROLE_BAR", "ROLE_BAZ" })
	public String roleBarBaz() {
		return "barbaz";
	}

	public String protected2() {
		return "PROTECTED2: " + name();
	}

	private String name() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null) {
			return authentication.getName();
		}
		return "<none>";
	}
}
