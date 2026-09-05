import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Song } from '../models/song.model';

@Injectable({ providedIn: 'root' })
export class AudioPlayerService {
  private audio = new Audio();

  private currentSongSubject = new BehaviorSubject<Song | null>(null);
  currentSong$ = this.currentSongSubject.asObservable();

  private isPlayingSubject = new BehaviorSubject<boolean>(false);
  isPlaying$ = this.isPlayingSubject.asObservable();

  private currentTimeSubject = new BehaviorSubject<number>(0);
  currentTime$ = this.currentTimeSubject.asObservable();

  private durationSubject = new BehaviorSubject<number>(0);
  duration$ = this.durationSubject.asObservable();

  constructor() {
    this.audio.preload = 'auto';

    this.audio.addEventListener('timeupdate', () => {
      this.currentTimeSubject.next(this.audio.currentTime);
    });

    this.audio.addEventListener('durationchange', () => {
      if (this.audio.duration && !isNaN(this.audio.duration)) {
        this.durationSubject.next(this.audio.duration);
      }
    });

    this.audio.addEventListener('loadedmetadata', () => {
      if (this.audio.duration && !isNaN(this.audio.duration)) {
        this.durationSubject.next(this.audio.duration);
      }
    });

    this.audio.addEventListener('ended', () => {
      this.isPlayingSubject.next(false);
      this.currentTimeSubject.next(0);
    });
  }

  playSong(song: Song): void {
    if (!song.audioUrl) return;

    // Toggle if same song
    if (this.currentSongSubject.value?.id === song.id && !this.audio.paused) {
      this.pause();
      return;
    }

    this.currentSongSubject.next(song);
    this.durationSubject.next(song.durationSeconds || 180);
    this.audio.src = song.audioUrl;
    this.audio.load();
    this.audio.play().then(() => {
      this.isPlayingSubject.next(true);
    }).catch(err => {
      console.error('Audio playback error', err);
    });
  }

  togglePlay(): void {
    if (this.audio.paused) {
      this.audio.play().then(() => {
        this.isPlayingSubject.next(true);
      }).catch(() => {});
    } else {
      this.pause();
    }
  }

  pause(): void {
    this.audio.pause();
    this.isPlayingSubject.next(false);
  }

  seek(seconds: number): void {
    this.audio.currentTime = seconds;
  }

  getCurrentSong(): Song | null {
    return this.currentSongSubject.value;
  }
}
