import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Song } from '../../../core/models';

@Component({
  selector: 'app-lyrics-dialog',
  template: `
    <div class="lyrics-dialog-container">
      <div class="lyrics-header">
        <div class="song-meta">
          <img [src]="data.song.albumCoverUrl" [alt]="data.song.title" class="album-thumb" *ngIf="data.song.albumCoverUrl">
          <div>
            <h2>{{ data.song.title }}</h2>
            <p>{{ data.song.artistName }}</p>
          </div>
        </div>
        <button mat-icon-button mat-dialog-close class="close-btn">
          <mat-icon>close</mat-icon>
        </button>
      </div>

      <div class="lyrics-body">
        <div *ngIf="data.loading" class="loading-state">
          <mat-spinner diameter="40"></mat-spinner>
          <p>Fetching lyrics...</p>
        </div>
        <pre *ngIf="!data.loading" class="lyrics-content">{{ data.lyrics }}</pre>
      </div>
    </div>
  `,
  styles: [`
    .lyrics-dialog-container {
      background: #181824;
      color: #fff;
      padding: 24px;
      border-radius: 12px;
      max-height: 80vh;
      display: flex;
      flex-direction: column;
    }
    .lyrics-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      border-bottom: 1px solid #2e2e42;
      padding-bottom: 16px;
      margin-bottom: 16px;
    }
    .song-meta {
      display: flex;
      align-items: center;
      gap: 16px;
      .album-thumb {
        width: 56px;
        height: 56px;
        border-radius: 8px;
        object-fit: cover;
      }
      h2 {
        margin: 0;
        font-size: 1.25rem;
        color: #ce93d8;
      }
      p {
        margin: 4px 0 0;
        color: #aaa;
        font-size: 0.9rem;
      }
    }
    .lyrics-body {
      overflow-y: auto;
      max-height: 60vh;
      padding-right: 8px;
    }
    .loading-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 40px;
      gap: 12px;
      color: #aaa;
    }
    .lyrics-content {
      font-family: 'Roboto', sans-serif;
      font-size: 1.05rem;
      line-height: 2;
      white-space: pre-wrap;
      color: #e0e0e0;
      text-align: center;
      margin: 0;
    }
  `]
})
export class LyricsDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { song: Song; lyrics: string; loading: boolean },
    public dialogRef: MatDialogRef<LyricsDialogComponent>
  ) {}
}
