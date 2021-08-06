package com.example.dataloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.dataloader.entity.Author;
import com.example.dataloader.entity.Comic;
import com.example.dataloader.repository.AuthorRepository;
import com.example.dataloader.repository.ComicRepository;

@Component
public class DataLoaderDemoData implements ApplicationRunner {

	private final ComicRepository comics;
	private final AuthorRepository authors;

	public DataLoaderDemoData(ComicRepository comics, AuthorRepository authors) {
		this.comics = comics;
		this.authors = authors;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		comics.save(new Comic("2.5次元の誘惑", authors.save(new Author("橋本悠")).getId()));
		comics.save(new Comic("SPY×FAMILY", authors.save(new Author("遠藤達哉")).getId()));
		comics.save(new Comic("ディザインズ", authors.save(new Author("五十嵐大介")).getId()));
		comics.save(new Comic("ベルセルク", authors.save(new Author("三浦建太郎")).getId()));
		comics.save(new Comic("ワールドトリガー", authors.save(new Author("葦原大介")).getId()));
		comics.save(new Comic("地獄楽", authors.save(new Author("賀来ゆうじ")).getId()));
		comics.save(new Comic("姫様“拷問”の時間です", authors.save(new Author("ひらけい")).getId()));
		comics.save(new Comic("無限の住人", authors.save(new Author("沙村広明")).getId()));
		comics.save(new Comic("焼いてるふたり", authors.save(new Author("ハナツカシオリ")).getId()));
		comics.save(new Comic("蟲師", authors.save(new Author("漆原友紀")).getId()));
	}
}
