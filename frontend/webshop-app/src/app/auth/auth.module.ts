import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { MessagesModule } from '../messages/messages.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthComponent } from './auth/auth.component';
import { AppMaterialModule } from '../app-material/app-material.module';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ActivateComponent } from './activate/activate.component';


@NgModule({
  declarations: [
    AuthComponent, LoginComponent, SignupComponent, ActivateComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    MessagesModule,
    AppMaterialModule
  ],
 
})
export class AuthModule { }
