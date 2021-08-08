import GraphiQL from 'graphiql';
import { createGraphiQLFetcher } from '@graphiql/toolkit';
import 'graphiql/graphiql.css';
import { createClient } from 'graphql-ws';

function myFetch(input, init) {
  return fetch(input, {
    // send Cookies
    credentials: 'include',
    ...init,
  });
}

const wsClient = createClient({
  url: 'ws://localhost:8080/graphql',
});

const fetcher = createGraphiQLFetcher({
  url: 'http://localhost:8080/graphql',
  fetch: myFetch,
  wsClient,
});

function App() {
  return (
    <GraphiQL fetcher={fetcher} defaultSecondaryEditorOpen={true} headerEditorEnabled={true} shouldPersistHeaders={true} />
  );
}

export default App;
