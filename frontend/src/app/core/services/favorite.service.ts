import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../models';
import { Song } from '../models/song.model';

@Injectable({ providedIn: 'root' })
export class FavoriteService {
  private base = `${environment.apiUrl}/favorites`;

  constructor(private http: HttpClient) {}

  getFavorites(): Observable<ApiResponse<Song[]>> {
    return this.http.get<ApiResponse<Song[]>>(this.base);
  }

  toggle(songId: number): Observable<ApiResponse<{ favorited: boolean }>> {
    return this.http.post<ApiResponse<{ favorited: boolean }>>(`${this.base}/${songId}`, {});
  }
}
