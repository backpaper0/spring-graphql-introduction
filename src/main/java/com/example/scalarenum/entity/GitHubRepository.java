package com.example.scalarenum.entity;

import java.net.URI;

public class GitHubRepository {

	private final URI uri;
	private final Visibility visibility;

	public GitHubRepository(URI uri, Visibility visibility) {
		this.uri = uri;
		this.visibility = visibility;
	}

	public URI getUri() {
		return uri;
	}

	public Visibility getVisibility() {
		return visibility;
	}
}
