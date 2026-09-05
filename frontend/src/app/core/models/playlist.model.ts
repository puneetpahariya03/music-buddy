import { Song } from './song.model';

export interface Playlist {
  id: number;
  name: string;
  description: string;
  isPublic: boolean;
  ownerUsername: string;
  songs: Song[];
  songCount: number;
  createdAt: string;
}
