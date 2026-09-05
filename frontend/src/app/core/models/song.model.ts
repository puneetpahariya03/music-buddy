export interface Song {
  id: number;
  title: string;
  durationSeconds: number;
  genre: string;
  audioUrl: string;
  artistName: string;
  artistId: number;
  albumTitle: string;
  albumId: number;
  albumCoverUrl: string;
  favorited: boolean;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}
