package com.example.security.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.security.service.SecurityDemoService;

@Controller
public class SecurityController {

	private final SecurityDemoService service;

	public SecurityController(SecurityDemoService service) {
		this.service = service;
	}

	@QueryMapping
	public Object security() {
		return new Object();
	}

	@SchemaMapping(typeName = "SecurityDemo", field = "public")
	public Object doPublic() {
		return service.doPublic();
	}

	@SchemaMapping(typeName = "SecurityDemo", field = "protected")
	public Object doProtected() {
		return service.doProtected();
	}

	@SchemaMapping(typeName = "SecurityDemo")
	public Object roleFoo() {
		return service.roleFoo();
	}

	@SchemaMapping(typeName = "SecurityDemo")
	public Object roleBarBaz() {
		return service.roleBarBaz();
	}

	@SchemaMapping(typeName = "SecurityDemo")
	public Object protected2() {
		return service.protected2();
	}
}
