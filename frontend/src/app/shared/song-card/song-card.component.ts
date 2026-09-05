import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Song } from '../../core/models/song.model';
import { AudioPlayerService } from '../../core/services/audio-player.service';

@Component({
  selector: 'app-song-card',
  templateUrl: './song-card.component.html',
  styleUrls: ['./song-card.component.scss']
})
export class SongCardComponent {
  @Input() song!: Song;
  @Input() isLoggedIn = false;
  @Output() favoriteToggled = new EventEmitter<Song>();
  @Output() addToPlaylist = new EventEmitter<Song>();

  constructor(public playerService: AudioPlayerService) {}

  formatDuration(seconds: number): string {
    if (!seconds) return '0:30';
    const m = Math.floor(seconds / 60);
    const s = seconds % 60;
    return `${m}:${s.toString().padStart(2, '0')}`;
  }

  play(): void {
    this.playerService.playSong(this.song);
  }
}
