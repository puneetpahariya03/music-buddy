import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject, debounceTime, distinctUntilChanged, takeUntil } from 'rxjs';
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
  loading = false;
  searchQuery = '';
  isLoggedIn = false;

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
    this.authService.isLoggedIn$
      .pipe(takeUntil(this.destroy$))
      .subscribe(v => this.isLoggedIn = v);

    this.searchSongs('bollywood hits');

    this.search$.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      takeUntil(this.destroy$)
    ).subscribe(q => {
      this.searchSongs(q.trim() || 'bollywood hits');
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  searchSongs(query: string): void {
    this.loading = true;
    this.songService.exploreiTunes(query).subscribe({
      next: res => {
        this.songs = res.data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onSearch(query: string): void {
    this.searchQuery = query;
    this.search$.next(query);
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
