import fetchGraphQL from './fetchGraphQL';
import useSWR from 'swr';

const fetcher = query => fetchGraphQL(query);

function App() {
  const { data, error } = useSWR(`{
    comics {
      id
      title
    }
  }`, fetcher);
  if (error) {
    return (<div>failed to load</div>);
  }
  if (!data) {
    return (<div>loading...</div>);
  }
  const { comics } = data;
  return (
    <div className="App">
      <ul>
        {comics.map(comic => <li key={comic.id}>
          {comic.title}
        </li>)}
      </ul>
    </div>
  );
}

export default App;
