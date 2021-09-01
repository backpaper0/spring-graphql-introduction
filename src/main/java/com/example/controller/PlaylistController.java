package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.example.controller.entity.Playlist;
import com.example.controller.entity.Singer;
import com.example.controller.entity.Song;
import com.example.controller.repository.PlaylistRepository;
import com.example.controller.repository.SingerRepository;
import com.example.controller.repository.SongRepository;

@Controller
public class PlaylistController {

	private final PlaylistRepository playlistRepository;
	private final SongRepository songRepository;
	private final SingerRepository singerRepository;

	public PlaylistController(PlaylistRepository playlistRepository, SongRepository songRepository,
			SingerRepository singerRepository) {
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
		this.singerRepository = singerRepository;
	}

	@QueryMapping
	public Optional<Playlist> playlist(@Argument Integer id) {
		return playlistRepository.findById(id);
	}

	@SchemaMapping
	public List<Song> songs(Playlist playlist) {
		Song probe = new Song();
		probe.setPlaylistId(playlist.getId());
		Example<Song> example = Example.of(probe);
		return songRepository.findAll(example);
	}

	@SchemaMapping
	public Optional<Singer> singer(Song song) {
		return singerRepository.findById(song.getSingerId());
	}
}
