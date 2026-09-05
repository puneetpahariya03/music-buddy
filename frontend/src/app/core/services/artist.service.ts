import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse, Artist } from '../models';

@Injectable({ providedIn: 'root' })
export class ArtistService {
  private base = `${environment.apiUrl}/artists`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<ApiResponse<Artist[]>> {
    return this.http.get<ApiResponse<Artist[]>>(this.base);
  }

  getById(id: number): Observable<ApiResponse<Artist>> {
    return this.http.get<ApiResponse<Artist>>(`${this.base}/${id}`);
  }
}
