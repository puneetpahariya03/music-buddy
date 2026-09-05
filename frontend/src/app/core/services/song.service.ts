import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse, PageResponse, Song } from '../models';

@Injectable({ providedIn: 'root' })
export class SongService {
  private base = `${environment.apiUrl}/songs`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 12): Observable<ApiResponse<PageResponse<Song>>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<ApiResponse<PageResponse<Song>>>(this.base, { params });
  }

  search(q: string, page = 0, size = 12): Observable<ApiResponse<PageResponse<Song>>> {
    const params = new HttpParams().set('q', q).set('page', page).set('size', size);
    return this.http.get<ApiResponse<PageResponse<Song>>>(`${this.base}/search`, { params });
  }

  exploreiTunes(query: string = 'top hits'): Observable<ApiResponse<Song[]>> {
    const params = new HttpParams().set('query', query);
    return this.http.get<ApiResponse<Song[]>>(`${this.base}/explore`, { params });
  }

  getById(id: number): Observable<ApiResponse<Song>> {
    return this.http.get<ApiResponse<Song>>(`${this.base}/${id}`);
  }
}
