import { Component, OnInit, OnDestroy } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import { Song } from '../../../core/models/song.model';
import { SongService } from '../../../core/services/song.service';
import { FavoriteService } from '../../../core/services/favorite.service';
import { AuthService } from '../../../core/services/auth.service';
import { AddToPlaylistDialogComponent } from '../add-to-playlist-dialog/add-to-playlist-dialog.component';

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.scss']
})
export class SongListComponent implements OnInit, OnDestroy {
  songs: Song[] = [];
  totalElements = 0;
  pageSize = 12;
  currentPage = 0;
  loading = false;
  searchQuery = '';
  isLoggedIn = false;
  activeTab: 'library' | 'explore' = 'explore'; // Default to iTunes Explore for real streaming

  private search$ = new Subject<string>();
  private destroy$ = new Subject<void>();

  constructor(
    private songService: SongService,
    private favoriteService: FavoriteService,
    private authService: AuthService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(v => this.isLoggedIn = v);
    this.fetchData();

    this.search$.pipe(
      debounceTime(400),
      distinctUntilChanged()
    ).subscribe(() => {
      this.currentPage = 0;
      this.fetchData();
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  switchTab(tab: 'library' | 'explore'): void {
    this.activeTab = tab;
    this.currentPage = 0;
    this.fetchData();
  }

  fetchData(): void {
    this.loading = true;
    if (this.activeTab === 'explore') {
      const q = this.searchQuery.trim() || 'top hits';
      this.songService.exploreiTunes(q).subscribe({
        next: res => {
          this.songs = res.data;
          this.totalElements = res.data.length;
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
    } else {
      const obs = this.searchQuery.trim()
        ? this.songService.search(this.searchQuery, this.currentPage, this.pageSize)
        : this.songService.getAll(this.currentPage, this.pageSize);

      obs.subscribe({
        next: res => {
          this.songs = res.data.content;
          this.totalElements = res.data.totalElements;
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
    }
  }

  onSearch(query: string): void {
    this.searchQuery = query;
    this.search$.next(query);
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.fetchData();
  }

  toggleFavorite(song: Song): void {
    if (!this.isLoggedIn) {
      this.snackBar.open('Please login to add favorites', 'Login', { duration: 3000 });
      return;
    }
    this.favoriteService.toggle(song.id).subscribe(res => {
      song.favorited = res.data['favorited'];
      this.snackBar.open(
        song.favorited ? '❤️ Added to favorites' : 'Removed from favorites', '', { duration: 2000 }
      );
    });
  }

  openAddToPlaylist(song: Song): void {
    if (!this.isLoggedIn) {
      this.snackBar.open('Please login to manage playlists', 'Login', { duration: 3000 });
      return;
    }
    this.dialog.open(AddToPlaylistDialogComponent, { data: { song }, width: '420px' });
  }
}
