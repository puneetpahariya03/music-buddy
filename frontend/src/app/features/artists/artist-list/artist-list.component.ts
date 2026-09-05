import { Component, OnInit } from '@angular/core';
import { Artist } from '../../../core/models/artist.model';
import { ArtistService } from '../../../core/services/artist.service';

@Component({
  selector: 'app-artist-list',
  templateUrl: './artist-list.component.html',
  styleUrls: ['./artist-list.component.scss']
})
export class ArtistListComponent implements OnInit {
  artists: Artist[] = [];
  loading = true;

  constructor(private artistService: ArtistService) {}

  ngOnInit(): void {
    this.artistService.getAll().subscribe({
      next: res => { this.artists = res.data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }
}
