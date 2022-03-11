interface Comic {
    id: string;
    title: string;
}

interface Comics {
    comics: Comic[];
}

export type { Comic, Comics };
