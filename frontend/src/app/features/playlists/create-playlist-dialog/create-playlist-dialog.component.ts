import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PlaylistService } from '../../../core/services/playlist.service';

@Component({
  selector: 'app-create-playlist-dialog',
  template: `
    <h2 mat-dialog-title>Create New Playlist</h2>
    <mat-dialog-content>
      <form [formGroup]="form">
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Playlist Name</mat-label>
          <input matInput formControlName="name">
          <mat-error *ngIf="form.get('name')?.hasError('required')">Name is required</mat-error>
        </mat-form-field>
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Description (optional)</mat-label>
          <textarea matInput formControlName="description" rows="3"></textarea>
        </mat-form-field>
        <mat-slide-toggle formControlName="isPublic" color="primary">
          Make Public
        </mat-slide-toggle>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-raised-button color="primary"
              (click)="create()" [disabled]="form.invalid || loading">
        Create
      </button>
    </mat-dialog-actions>
  `,
  styles: ['.full-width { width:100%; margin-bottom:12px; display:block; }']
})
export class CreatePlaylistDialogComponent {
  form: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private playlistService: PlaylistService,
    private dialogRef: MatDialogRef<CreatePlaylistDialogComponent>,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      name:        ['', [Validators.required]],
      description: [''],
      isPublic:    [false]
    });
  }

  create(): void {
    if (this.form.invalid) return;
    this.loading = true;
    this.playlistService.create(this.form.value).subscribe({
      next: () => {
        this.snackBar.open('✅ Playlist created!', '', { duration: 2000 });
        this.dialogRef.close(true);
      },
      error: () => {
        this.loading = false;
        this.snackBar.open('Failed to create playlist', 'Close', { duration: 3000 });
      }
    });
  }
}
