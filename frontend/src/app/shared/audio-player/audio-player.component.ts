import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AudioPlayerService } from '../../core/services/audio-player.service';
import { SongService } from '../../core/services/song.service';
import { LyricsDialogComponent } from './lyrics-dialog/lyrics-dialog.component';

@Component({
  selector: 'app-audio-player',
  templateUrl: './audio-player.component.html',
  styleUrls: ['./audio-player.component.scss']
})
export class AudioPlayerComponent {
  constructor(
    public playerService: AudioPlayerService,
    private songService: SongService,
    private dialog: MatDialog
  ) {}

  formatTime(seconds: number): string {
    if (!seconds || isNaN(seconds)) return '0:00';
    const mins = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);
    return `${mins}:${secs.toString().padStart(2, '0')}`;
  }

  onSeek(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.playerService.seek(Number(input.value));
  }

  openLyrics(): void {
    const current = this.playerService.getCurrentSong();
    if (!current) return;

    const dialogData = {
      song: current,
      lyrics: '',
      loading: true
    };

    const dialogRef = this.dialog.open(LyricsDialogComponent, {
      width: '550px',
      data: dialogData,
      panelClass: 'dark-dialog'
    });

    this.songService.getLyrics(current.artistName, current.title).subscribe({
      next: res => {
        dialogData.lyrics = res.data;
        dialogData.loading = false;
      },
      error: () => {
        dialogData.lyrics = `Lyrics for "${current.title}" by ${current.artistName} are currently unavailable.`;
        dialogData.loading = false;
      }
    });
  }
}
