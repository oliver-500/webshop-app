import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import {MatSidenavModule} from '@angular/material/sidenav';

const materialModules = [
  MatButtonModule,
  MatSlideToggleModule,  
  MatFormFieldModule,
  FormsModule,
  MatInputModule,
  ReactiveFormsModule,
  MatSnackBarModule,
  MatCardModule,
  MatProgressSpinnerModule,
  MatToolbarModule,
  MatMenuModule,
  MatIconModule,
  MatDialogModule,
  MatFormFieldModule,
  MatSelectModule,
  MatPaginatorModule,
  MatCheckboxModule,
  MatTableModule,
  MatSidenavModule
]

@NgModule({
  declarations: [],
  imports: [
    ...materialModules
  ],
  exports: [
    ...materialModules
  ]
})
export class AppMaterialModule { }
