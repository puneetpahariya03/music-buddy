import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Song } from '../../../core/models/song.model';
import { FavoriteService } from '../../../core/services/favorite.service';

@Component({
  selector: 'app-favorites-list',
  templateUrl: './favorites-list.component.html',
  styleUrls: ['./favorites-list.component.scss']
})
export class FavoritesListComponent implements OnInit {
  songs: Song[] = [];
  loading = true;

  constructor(
    private favoriteService: FavoriteService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.favoriteService.getFavorites().subscribe({
      next: res => { this.songs = res.data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  toggleFavorite(song: Song): void {
    this.favoriteService.toggle(song.id).subscribe(res => {
      song.favorited = res.data['favorited'];
      if (!song.favorited) {
        this.songs = this.songs.filter(s => s.id !== song.id);
        this.snackBar.open('Removed from favorites', '', { duration: 2000 });
      }
    });
  }
}
