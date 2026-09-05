import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PlaylistService } from '../../../core/services/playlist.service';
import { Playlist } from '../../../core/models/playlist.model';
import { Song } from '../../../core/models/song.model';

@Component({
  selector: 'app-add-to-playlist-dialog',
  template: `
    <h2 mat-dialog-title>Add to Playlist</h2>
    <mat-dialog-content>
      <p style="color:#aaa; margin-bottom:12px;">
        Adding: <strong style="color:#fff;">{{ data.song.title }}</strong>
      </p>
      <div *ngIf="playlists.length === 0" style="color:#888; padding:16px 0;">
        You have no playlists yet. Create one from the Playlists page!
      </div>
      <mat-selection-list #list [multiple]="false">
        <mat-list-option *ngFor="let pl of playlists" [value]="pl">
          <mat-icon matListItemIcon>queue_music</mat-icon>
          <span matListItemTitle>{{ pl.name }}</span>
          <span matListItemLine>{{ pl.songCount }} songs</span>
        </mat-list-option>
      </mat-selection-list>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-raised-button color="primary"
              (click)="addToSelected(list.selectedOptions.selected[0].value)"
              [disabled]="!list.selectedOptions.selected.length">
        Add Song
      </button>
    </mat-dialog-actions>
  `
})
export class AddToPlaylistDialogComponent implements OnInit {
  playlists: Playlist[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { song: Song },
    private playlistService: PlaylistService,
    private dialogRef: MatDialogRef<AddToPlaylistDialogComponent>,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.playlistService.getMyPlaylists().subscribe(res => this.playlists = res.data);
  }

  addToSelected(playlist: Playlist): void {
    if (!playlist) return;
    this.playlistService.addSong(playlist.id, this.data.song.id).subscribe({
      next: () => {
        this.snackBar.open(`✅ Added to "${playlist.name}"`, '', { duration: 2000 });
        this.dialogRef.close(true);
      },
      error: () => this.snackBar.open('Failed to add song', 'Close', { duration: 3000 })
    });
  }
}
