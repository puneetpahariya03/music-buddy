import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse, Playlist } from '../models';

@Injectable({ providedIn: 'root' })
export class PlaylistService {
  private base = `${environment.apiUrl}/playlists`;

  constructor(private http: HttpClient) {}

  getMyPlaylists(): Observable<ApiResponse<Playlist[]>> {
    return this.http.get<ApiResponse<Playlist[]>>(this.base);
  }

  getById(id: number): Observable<ApiResponse<Playlist>> {
    return this.http.get<ApiResponse<Playlist>>(`${this.base}/${id}`);
  }

  create(data: { name: string; description: string; isPublic: boolean }): Observable<ApiResponse<Playlist>> {
    return this.http.post<ApiResponse<Playlist>>(this.base, data);
  }

  update(id: number, data: { name: string; description: string; isPublic: boolean }): Observable<ApiResponse<Playlist>> {
    return this.http.put<ApiResponse<Playlist>>(`${this.base}/${id}`, data);
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.base}/${id}`);
  }

  addSong(playlistId: number, songId: number): Observable<ApiResponse<Playlist>> {
    return this.http.post<ApiResponse<Playlist>>(`${this.base}/${playlistId}/songs/${songId}`, {});
  }

  removeSong(playlistId: number, songId: number): Observable<ApiResponse<Playlist>> {
    return this.http.delete<ApiResponse<Playlist>>(`${this.base}/${playlistId}/songs/${songId}`);
  }
}
