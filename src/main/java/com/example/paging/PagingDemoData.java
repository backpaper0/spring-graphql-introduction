package com.example.paging;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.paging.entity.Commit;
import com.example.paging.repository.CommitRepository;

@Component
public class PagingDemoData implements ApplicationRunner {

	private final CommitRepository commits;

	public PagingDemoData(CommitRepository commits) {
		this.commits = commits;
	}

	@Override
	public void run(ApplicationArguments args) {
		commits.save(new Commit("1f1277c", "lombokでjacocoどーすんの問題への対応を試す"));
		commits.save(new Commit("2f41e23", "JSESSIONIDと異なる名前を使う例"));
		commits.save(new Commit("0db79a2", "Initial commit"));
		commits.save(new Commit("56a379b", "typeormを試す"));
		commits.save(new Commit("6f868f0", "リスナーをorm.xmlで定義する"));
		commits.save(new Commit("6da35b4", "MappedSuperclassも試す"));
		commits.save(new Commit("045edf0", "ロギングなんかを修正したり"));
		commits.save(new Commit("f8bfd21", "orm.xmlでオーバーライドしてVersionを無視する例"));
		commits.save(new Commit("0312b17", "Hibernateでトランザクションタイムアウトを設定する例"));
		commits.save(new Commit("04e6d0c", "Mavenのプロファイルを学ぶ"));
		commits.save(new Commit("3c433e6", "キーの削除、タイムアウトを検知する"));
		commits.save(new Commit("8618885", "multi stage buildを学ぶ"));
		commits.save(new Commit("08a67bc", "build-argを学ぶ"));
		commits.save(new Commit("d8e6847", "リソースの制限を試す"));
		commits.save(new Commit("4a6ce8e", "JavaでAWS Lambda"));
		commits.save(new Commit("9b44232", "コンテナーを使う"));
		commits.save(new Commit("a590ab8", "Inherited"));
		commits.save(new Commit("9fa0e0a", "System.Logger"));
		commits.save(new Commit("76bff7e", "AWS Lambda カスタムランタイムの小さなデモ"));
		commits.save(new Commit("6b1d9dc", "babelでReact"));
		commits.save(new Commit("d318e66", "Babelを試す"));
		commits.save(new Commit("77d1e61", "Update"));
		commits.save(new Commit("d4079ab", "Jestでテストする"));
		commits.save(new Commit("a1753d9", "webpackを試す"));
		commits.save(new Commit("6d952b3", "static initializerでスローされた例外をcatchする"));
		commits.save(new Commit("7e4ece3", "高階関数"));
		commits.save(new Commit("1f8e3d8", "JavaっぽくDIする"));
		commits.save(new Commit("438eb72", "コンパイラの警告を抑制"));
		commits.save(new Commit("70c535e", "ファイル分割"));
		commits.save(new Commit("8318b8a", "Makefile"));
		commits.save(new Commit("1ad86d3", "Karateを試す"));
		commits.save(new Commit("65d47e4", "複数のバイナリを含める"));
		commits.save(new Commit("dd43c96", "相互に呼び出す"));
		commits.save(new Commit("a9d04ba", "にゃーん"));
		commits.save(new Commit("c08c72f", "環境変数"));
		commits.save(new Commit("ea18f76", "にゃーん"));
		commits.save(new Commit("5e11c25", "Dockerでrustコードをビルド、実行"));
		commits.save(new Commit("93e7885", "改善(になってるかな？)"));
		commits.save(new Commit("8dc7978", "Vecと所有権を理解したい"));
		commits.save(new Commit("1e6b129", "ぬーん"));
		commits.save(new Commit("471159a", "panic!じゃなくてResultにした"));
		commits.save(new Commit("b64bcea", "いろいろ"));
		commits.save(new Commit("a3dae8c", "READMEを追加"));
		commits.save(new Commit("be273fb", "main関数を実装"));
		commits.save(new Commit("e21d034", "ASTの評価"));
		commits.save(new Commit("36765f1", "パーサー"));
		commits.save(new Commit("770f1d6", "足し算の字句解析器"));
		commits.save(new Commit("d21607a", "リネーム"));
		commits.save(new Commit("5749862", "非同期処理の後始末"));
		commits.save(new Commit("7207e61", "コードを整理した"));
	}
}
