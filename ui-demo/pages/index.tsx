import type { NextPage } from 'next';
import useSWR from 'swr';
import fetchGraphQL from '../lib/fetchGraphQL';
import type { Comics } from '../lib/types';

function fetcher<T>(query: string): Promise<T> {
  return fetchGraphQL<T>(query);
}

const App: NextPage = () => {
  const { data, error } = useSWR<Comics>(`{
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
