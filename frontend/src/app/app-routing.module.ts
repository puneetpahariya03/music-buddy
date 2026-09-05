import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/songs', pathMatch: 'full' },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'songs',
    loadChildren: () => import('./features/songs/songs.module').then(m => m.SongsModule)
  },
  {
    path: 'artists',
    loadChildren: () => import('./features/artists/artists.module').then(m => m.ArtistsModule)
  },
  {
    path: 'playlists',
    canActivate: [authGuard],
    loadChildren: () => import('./features/playlists/playlists.module').then(m => m.PlaylistsModule)
  },
  {
    path: 'favorites',
    canActivate: [authGuard],
    loadChildren: () => import('./features/favorites/favorites.module').then(m => m.FavoritesModule)
  },
  { path: '**', redirectTo: '/songs' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
