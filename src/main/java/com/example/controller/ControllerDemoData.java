package com.example.controller;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.controller.entity.Playlist;
import com.example.controller.entity.Singer;
import com.example.controller.entity.Song;
import com.example.controller.repository.PlaylistRepository;
import com.example.controller.repository.SingerRepository;
import com.example.controller.repository.SongRepository;

@Component
public class ControllerDemoData implements ApplicationRunner {

	private final PlaylistRepository playlistRepository;
	private final SongRepository songRepository;
	private final SingerRepository singerRepository;

	public ControllerDemoData(PlaylistRepository playlistRepository, SongRepository songRepository,
			SingerRepository singerRepository) {
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
		this.singerRepository = singerRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Playlist playlist1 = playlistRepository.save(new Playlist("Playlist 1"));

		Singer lunaSea = singerRepository.save(new Singer("LUNA SEA"));
		Singer larc = singerRepository.save(new Singer("L'Arc-en-Ciel"));
		Singer tmge = singerRepository.save(new Singer("THEE MICHELLE GUN ELEPHANT"));

		songRepository.save(new Song("Limit", playlist1.getId(), lunaSea.getId()));
		songRepository.save(new Song("Unlikelihood", playlist1.getId(), lunaSea.getId()));
		songRepository.save(new Song("MECHANICAL DANCE", playlist1.getId(), lunaSea.getId()));

		songRepository.save(new Song("LOVE FLIES", playlist1.getId(), larc.getId()));
		songRepository.save(new Song("真実と幻想と", playlist1.getId(), larc.getId()));
		songRepository.save(new Song("Inner Core", playlist1.getId(), larc.getId()));

		songRepository.save(new Song("暴かれた世界", playlist1.getId(), tmge.getId()));
		songRepository.save(new Song("G.W.D", playlist1.getId(), tmge.getId()));
		songRepository.save(new Song("デッドマンズ・ギャラクシー・デイズ", playlist1.getId(), tmge.getId()));
	}
}
