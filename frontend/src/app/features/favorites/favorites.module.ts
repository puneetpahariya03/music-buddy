import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SharedModule } from '../../shared/shared.module';
import { FavoritesListComponent } from './favorites-list/favorites-list.component';

const routes: Routes = [{ path: '', component: FavoritesListComponent }];

@NgModule({
  declarations: [FavoritesListComponent],
  imports: [SharedModule, RouterModule.forChild(routes), MatProgressSpinnerModule]
})
export class FavoritesModule {}
