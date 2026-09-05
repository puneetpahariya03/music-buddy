import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { SharedModule } from '../../shared/shared.module';
import { SongListComponent } from './song-list/song-list.component';
import { AddToPlaylistDialogComponent } from './add-to-playlist-dialog/add-to-playlist-dialog.component';

const routes: Routes = [{ path: '', component: SongListComponent }];

@NgModule({
  declarations: [SongListComponent, AddToPlaylistDialogComponent],
  imports: [
    SharedModule, FormsModule,
    RouterModule.forChild(routes),
    MatFormFieldModule, MatInputModule, MatPaginatorModule,
    MatProgressSpinnerModule, MatDialogModule, MatListModule
  ]
})
export class SongsModule {}
