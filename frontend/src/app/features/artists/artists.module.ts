import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SharedModule } from '../../shared/shared.module';
import { ArtistListComponent } from './artist-list/artist-list.component';

const routes: Routes = [{ path: '', component: ArtistListComponent }];

@NgModule({
  declarations: [ArtistListComponent],
  imports: [SharedModule, RouterModule.forChild(routes), MatProgressSpinnerModule]
})
export class ArtistsModule {}
