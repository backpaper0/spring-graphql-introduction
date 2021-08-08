package com.example.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityDemoService {

	public String doPublic() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return "PUBLIC: " + authentication.getName();
	}

	@PreAuthorize("isAuthenticated()")
	public String doProtected() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return "PROTECTED: " + authentication.getName();
	}
}
