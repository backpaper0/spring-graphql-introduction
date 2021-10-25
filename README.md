# Spring GraphQL introduction

[![CI for Spring GraphQL introduction example code](https://github.com/backpaper0/spring-graphql-introduction/actions/workflows/ci.yml/badge.svg)](https://github.com/backpaper0/spring-graphql-introduction/actions/workflows/ci.yml)


<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [Spring GraphQL introduction](#spring-graphql-introduction)
  - [概要](#概要)
  - [資料のビルド](#資料のビルド)
  - [デモの手順](#デモの手順)
    - [準備](#準備)
      - [※GraphiQLについて](#graphiqlについて)
    - [query操作](#query操作)
    - [subscription](#subscription)
    - [DataLoader](#dataloader)
    - [ページング](#ページング)
    - [WIP: 認証・認可](#wip-認証認可)
    - [メトリクス](#メトリクス)
  - [ライセンス](#ライセンス)

<!-- /code_chunk_output -->

## 概要

[JSUG勉強会 2021年その2 Spring GraphQLをとことん語る夕べ](https://jsug.doorkeeper.jp/events/124798)での発表のスライドとコード例です。
発表時はSpring GraphQL 1.0.0-M1でしたがM2が出たためスライドを更新しました。

## 資料のビルド

PlantUMLで描いた図をビルドする。

```bash
java -jar ~/plantuml.jar -tsvg docs/plantuml.pu
```

スライドをビルドする。

```bash
npx @marp-team/marp-cli@latest --html --output docs/index.html docs/slide.md
```

`/docs`をGitHub Pagesでホスティングするように設定しているので次のURLでスライドが見られる。

- https://backpaper0.github.io/spring-graphql-introduction/

## デモの手順

### 準備

```
./mvnw spring-boot:run
```

ブラウザで http://localhost:8080/my-graphiql を開く。

#### ※GraphiQLについて

Spring GraphQLにビルトインされているGraphiQLは認証と`subscription`操作に対応していないため独自に調整したGraphiQLを用意している。

ソースコードは`my-graphiql`にある。

`create-react-app`で作ったReactアプリケーションとなっていて、カスタマイズしたい場合はまず`npm install`で依存ライブラリを準備する。

それから`npm start`で起動する。
カスタマイズ中はこちらで動作確認しながら開発を進めると良い。
なお、このためにSpring Bootアプリケーション側でCORSの設定を入れている。

Spring Bootアプリケーションに組み込むには、まずSpring Bootアプリケーション側の`src/main/resources/static/my-graphiql`を削除する。
それから`npm run build`を実施すると`src/main/resources/static/my-graphiql`にビルドされたHTMLやJSファイルが書き出される。
あとは`./mvnw spring-boot:run`をすれば良い。

### query操作

スライドにもあったクエリーを試す。

```gql
query {
  article(id: 1) {
    id
    title
    content
    category {
      id
      name
    }
  }
}
```

タイトルだけ取得するようにしてみる。

```gql
query {
  article(id: 1) {
    title
  }
}
```

変数を使ってみる。

```gql
query GetArticle($id: ID!) {
  article(id: $id) {
    title
  }
}
```

```json
{
  "id": 1
}
```

`curl`でも試してみる。

```
curl -s http://localhost:8080/graphql -H "Content-Type: application/json" -d '{"query": "{article(id: 1) { id, title, content, category { id, name } }}"}' | jq
```

### subscription

`subscription`操作も試してみる。

```gql
subscription {
  count
}
```

結果のエリアにカウントアップされて1から10まで表示される。

`wscat`でも確認してみる。
`wscat`は`npm install -g wscat`でインストールできる。

```
wscat --connect ws://localhost:8080/graphql
```

`subscription`のプロトコルはまだ理解していないので、Spring GraphQLのコードを読んでわかった手順を実施する。

まずは`connection_init`が必要。

```
{"type": "connection_init"}
```

それから`subscribe`。
待っていると1秒おきにカウントアップする値が返される。

```
{"type": "subscribe", "id": "...", "payload": {"query": "subscription { count }"}}
```

もちろん変数も使える。

```
{"type": "subscribe", "id": "...", "payload": {"query": "subscription Count($size: Int!) { count(size: $size) }", "variables": {"size": 5 }}}
```

### DataLoader

まずはN + 1。

```gql
query {
  comics {
    title
    author {
      name
    }
  }
}
```

`Fetch Query.comics`以降のログを見ると`comics`で1回、`comics.author`で10回のクエリーが発行されていることがわかる。

次にDataLoader版。

```gql
query {
  comics {
    title
    author(useDataLoader: true) {
      name
    }
  }
}
```

`Fetch Query.comics`以降のログを見ると`comics`と`comics.author`が共に1回ずつのクエリー発行で済んでいることがわかる。

### ページング

まずは`after`を指定せずクエリーーを発行して返ってくる値を確認する。

```gql
query GitCommits {
  history {
    forward(first: 3) {
      edges {
        node {
          hash
          message
        }
        cursor
      }
      pageInfo {
        hasPreviousPage
        hasNextPage
        startCursor
        endCursor
      }
    }
  }
}
```

それから`pageInfo`の値を見ながら`after`を設定しつつクエリーを試す。

```gql
query GitCommits {
  history {
    forward(first: 3, after: "3") {
      edges {
        node {
          hash
          message
        }
        cursor
      }
      pageInfo {
        hasPreviousPage
        hasNextPage
        startCursor
        endCursor
      }
    }
  }
}
```

後方も試す。

```gql
query GitCommits {
  history {
    backward(last: 3, before: "7") {
      edges {
        node {
          hash
          message
        }
        cursor
      }
      pageInfo {
        hasPreviousPage
        hasNextPage
        startCursor
        endCursor
      }
    }
  }
}
```

### WIP: 認証・認可

次のクエリーを実行するとエラー(`Unauthorized`)になる。

```gql
{
  security {
    protected
  }
}
```

REQUEST HEADERSという場所に次のJSONを書いて実行するとエラーにならず値が返ってくる。

```json
{
  "Authorization": "Basic ZGVtbzpzZWNyZXQ="
}
```

これは該当の`DataFetcher`内で呼び出されているコンポーネントのメソッドに`@PreAuthorize("isAuthenticated()")`を付けている。

カスタム`directive`で認証を表現した例も作ってみた。
次のクエリーが`@authenticated`というカスタム`directive`で保護したフィールドへのアクセスとなる。

```gql
{
  security {
    protected2
  }
}
```

`Authorization`ヘッダーの有無による違いを試してみてほしい。

### メトリクス

```
curl -s localhost:8080/actuator/metrics/graphql.request | jq
```

```
curl -s localhost:8080/actuator/metrics/graphql.datafetcher | jq
```

```
curl -s localhost:8080/actuator/metrics/graphql.error | jq
```

---

## ライセンス

スライド(`docs/`配下にあるファイル)は[CC BY 4.0](https://creativecommons.org/licenses/by/4.0/)、ソースコード(スライド以外のファイル)は[MIT](https://opensource.org/licenses/mit-license.php)を適用します。
