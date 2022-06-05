package com.example.dataloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.dataloader.entity.Author;
import com.example.dataloader.entity.Comic;
import com.example.dataloader.entity.Publisher;
import com.example.dataloader.repository.AuthorRepository;
import com.example.dataloader.repository.ComicRepository;
import com.example.dataloader.repository.PublisherRepository;

@Component
public class DataLoaderDemoData implements ApplicationRunner {

	private final ComicRepository comics;
	private final AuthorRepository authors;
	private final PublisherRepository publishers;

	public DataLoaderDemoData(ComicRepository comics, AuthorRepository authors, PublisherRepository publishers) {
		this.comics = comics;
		this.authors = authors;
		this.publishers = publishers;
	}

	@Override
	public void run(ApplicationArguments args) {
		Publisher publisher1 = publishers.save(new Publisher("集英社"));
		Publisher publisher2 = publishers.save(new Publisher("講談社"));
		Publisher publisher3 = publishers.save(new Publisher("白泉社"));
		publishers.save(new Publisher("白泉社"));
		publishers.save(new Publisher("白泉社"));
		comics.save(new Comic("2.5次元の誘惑", authors.save(new Author("橋本悠")), publisher1));
		comics.save(new Comic("SPY×FAMILY", authors.save(new Author("遠藤達哉")), publisher1));
		comics.save(new Comic("ディザインズ", authors.save(new Author("五十嵐大介")), publisher2));
		comics.save(new Comic("ベルセルク", authors.save(new Author("三浦建太郎")), publisher3));
		comics.save(new Comic("ワールドトリガー", authors.save(new Author("葦原大介")), publisher1));
		comics.save(new Comic("地獄楽", authors.save(new Author("賀来ゆうじ")), publisher1));
		comics.save(new Comic("姫様“拷問”の時間です", authors.save(new Author("ひらけい")), publisher1));
		comics.save(new Comic("無限の住人", authors.save(new Author("沙村広明")), publisher2));
		comics.save(new Comic("焼いてるふたり", authors.save(new Author("ハナツカシオリ")), publisher2));
		comics.save(new Comic("蟲師", authors.save(new Author("漆原友紀")), publisher2));
	}
}
