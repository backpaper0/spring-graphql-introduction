package com.example.paging.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.paging.entity.Commit;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;

@Controller
public class CommitController {

	private final EntityManager em;

	public CommitController(EntityManager em) {
		this.em = em;
	}

	@QueryMapping
	public Object history() {
		return new Object();
	}

	@SchemaMapping(typeName = "History")
	public Connection<Commit> forward(@Argument int first, @Argument(required = false) String after) {
		return fetchData(em, true, first, after);
	}

	@SchemaMapping(typeName = "History")
	public Connection<Commit> backward(@Argument int last, @Argument(required = false) String before) {
		return fetchData(em, false, last, before);
	}

	private static Connection<Commit> fetchData(EntityManager em, boolean forward, int pageSize,
			String boundaryCursor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Commit> q = cb.createQuery(Commit.class);
		Root<Commit> root = q.from(Commit.class);
		Path<Integer> idAttribute = root.get("id");
		if (boundaryCursor != null) {
			if (forward) {
				q.where(cb.greaterThanOrEqualTo(idAttribute, Integer.valueOf(boundaryCursor)));
			} else {
				q.where(cb.lessThanOrEqualTo(idAttribute, Integer.valueOf(boundaryCursor)));
			}
		}
		q.orderBy(forward ? cb.asc(idAttribute) : cb.desc(idAttribute));

		List<Commit> commits = em.createQuery(q).setMaxResults(pageSize + 2).getResultList();

		boolean includeBoundary = boundaryCursor != null && commits.isEmpty() == false
				&& commits.get(0).getId().equals(Integer.valueOf(boundaryCursor));
		if (includeBoundary) {
			commits = commits.subList(1, commits.size());
		}

		List<Edge<Commit>> edges = commits.stream().limit(pageSize)
				.sorted(Comparator.comparing(Commit::getId))
				.map(commit -> new DefaultEdge<>(commit, new DefaultConnectionCursor(commit.getId().toString())))
				.collect(Collectors.toList());

		ConnectionCursor startCursor;
		if (edges.isEmpty()) {
			startCursor = null;
		} else {
			startCursor = edges.get(0).getCursor();
		}
		ConnectionCursor endCursor;
		if (edges.isEmpty()) {
			endCursor = null;
		} else {
			endCursor = edges.get(edges.size() - 1).getCursor();
		}
		boolean hasPreviousPage = forward ? includeBoundary : commits.size() > pageSize;
		boolean hasNextPage = forward ? commits.size() > pageSize : includeBoundary;
		PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, hasPreviousPage, hasNextPage);
		return new DefaultConnection<>(edges, pageInfo);
	}
}
