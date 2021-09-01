package com.example.paging.wiring;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

import com.example.paging.entity.Commit;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;

@Component
public class CommitDataWiring implements RuntimeWiringConfigurer {

	private final EntityManager em;

	public CommitDataWiring(EntityManager em) {
		this.em = em;
	}

	@Override
	public void configure(RuntimeWiring.Builder paramBuilder) {
		paramBuilder.type("Query", b -> b.dataFetcher("history", env -> new Object()));

		paramBuilder.type("History", b -> b
				.dataFetcher("forward", dataFetcher(em, true))
				.dataFetcher("backward", dataFetcher(em, false)));
	}

	private static DataFetcher<Connection<Commit>> dataFetcher(EntityManager em, boolean forward) {
		return env -> {
			int pageSize = env.getArgument(forward ? "first" : "last");
			String boundaryCursor = env.getArgument(forward ? "after" : "before");

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
		};
	}
}
