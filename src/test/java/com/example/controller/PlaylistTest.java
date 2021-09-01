package com.example.controller;

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
public class PlaylistTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void article() {
		String query = "{" +
				"  playlist(id: 1) {" +
				"    name" +
				"    songs {" +
				"      title" +
				"      singer {" +
				"        name" +
				"      }" +
				"    }" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.execute()

				.path("playlist.name")
				.entity(String.class)
				.isEqualTo("Playlist 1")

				.path("playlist.songs[*].title")
				.entityList(String.class)
				.isEqualTo(List.of(
						"Limit",
						"Unlikelihood",
						"MECHANICAL DANCE",

						"LOVE FLIES",
						"真実と幻想と",
						"Inner Core",

						"暴かれた世界",
						"G.W.D",
						"デッドマンズ・ギャラクシー・デイズ"))

				.path("playlist.songs[*].singer.name")
				.entityList(String.class)
				.isEqualTo(List.of(
						"LUNA SEA", "LUNA SEA", "LUNA SEA",
						"L'Arc-en-Ciel", "L'Arc-en-Ciel", "L'Arc-en-Ciel",
						"THEE MICHELLE GUN ELEPHANT", "THEE MICHELLE GUN ELEPHANT", "THEE MICHELLE GUN ELEPHANT"));
	}
}
