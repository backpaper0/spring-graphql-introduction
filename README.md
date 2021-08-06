# Spring GraphQL introduction

## 概要

[JSUG勉強会 2021年その2 Spring GraphQLをとことん語る夕べ](https://jsug.doorkeeper.jp/events/124798)での発表のスライドとコード例です。

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

ブラウザで http://localhost:8080/graphiql を開く。

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

Spring GraphQLに組み込まれているGraphiQLは`subscription`へ対応していないようなので`wscat`で確認してみる。
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
