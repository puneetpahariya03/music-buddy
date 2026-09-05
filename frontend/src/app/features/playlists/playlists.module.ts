import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SharedModule } from '../../shared/shared.module';
import { PlaylistListComponent } from './playlist-list/playlist-list.component';
import { PlaylistDetailComponent } from './playlist-detail/playlist-detail.component';
import { CreatePlaylistDialogComponent } from './create-playlist-dialog/create-playlist-dialog.component';

const routes: Routes = [
  { path: '', component: PlaylistListComponent },
  { path: ':id', component: PlaylistDetailComponent }
];

@NgModule({
  declarations: [PlaylistListComponent, PlaylistDetailComponent, CreatePlaylistDialogComponent],
  imports: [
    SharedModule, ReactiveFormsModule,
    RouterModule.forChild(routes),
    MatProgressSpinnerModule, MatDialogModule,
    MatFormFieldModule, MatInputModule, MatSlideToggleModule,
    MatListModule, MatTooltipModule
  ]
})
export class PlaylistsModule {}
