import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UserPanelContainerComponent } from './user-panel-container/user-panel-container.component';
import { UserPanelComponent } from './user-panel/user-panel.component';
import { AppMaterialModule } from '../app-material/app-material.module';


@NgModule({
  declarations: [
    UserPanelContainerComponent,
    UserPanelComponent
  ],
  imports: [
    CommonModule,
    AppMaterialModule,
    UsersRoutingModule
  ]
})
export class UsersModule { }
