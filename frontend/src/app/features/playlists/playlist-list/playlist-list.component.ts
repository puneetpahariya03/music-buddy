import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Playlist } from '../../../core/models/playlist.model';
import { PlaylistService } from '../../../core/services/playlist.service';
import { CreatePlaylistDialogComponent } from '../create-playlist-dialog/create-playlist-dialog.component';

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.scss']
})
export class PlaylistListComponent implements OnInit {
  playlists: Playlist[] = [];
  loading = true;

  constructor(
    private playlistService: PlaylistService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true;
    this.playlistService.getMyPlaylists().subscribe({
      next: res => { this.playlists = res.data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  openCreate(): void {
    const ref = this.dialog.open(CreatePlaylistDialogComponent, { width: '420px' });
    ref.afterClosed().subscribe(created => { if (created) this.load(); });
  }

  open(id: number): void { this.router.navigate(['/playlists', id]); }

  delete(playlist: Playlist, event: MouseEvent): void {
    event.stopPropagation();
    if (!confirm(`Delete "${playlist.name}"? This cannot be undone.`)) return;
    this.playlistService.delete(playlist.id).subscribe(() => {
      this.snackBar.open('Playlist deleted', '', { duration: 2000 });
      this.load();
    });
  }
}
