async function fetchGraphQL<T>(text: string, variables?: any): Promise<T> {

    const response = await fetch('http://localhost:8080/graphql', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            query: text,
            variables,
        }),
    });

    const json = await response.json();
    // if (json.errors) {
    //     const e = new Error();
    //     e.errors = json.errors;
    //     throw e;
    // }
    return json.data;
}

export default fetchGraphQL;
