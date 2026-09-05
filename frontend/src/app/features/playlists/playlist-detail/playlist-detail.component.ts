import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Playlist } from '../../../core/models/playlist.model';
import { PlaylistService } from '../../../core/services/playlist.service';

@Component({
  selector: 'app-playlist-detail',
  templateUrl: './playlist-detail.component.html',
  styleUrls: ['./playlist-detail.component.scss']
})
export class PlaylistDetailComponent implements OnInit {
  playlist: Playlist | null = null;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private playlistService: PlaylistService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.playlistService.getById(id).subscribe({
      next: res => { this.playlist = res.data; this.loading = false; },
      error: () => { this.loading = false; this.router.navigate(['/playlists']); }
    });
  }

  removeSong(songId: number): void {
    if (!this.playlist) return;
    this.playlistService.removeSong(this.playlist.id, songId).subscribe(res => {
      this.playlist = res.data;
      this.snackBar.open('Song removed', '', { duration: 2000 });
    });
  }
}
