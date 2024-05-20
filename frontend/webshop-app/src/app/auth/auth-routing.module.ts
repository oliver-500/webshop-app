import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ActivateComponent } from './activate/activate.component';


const routes: Routes = [{
  path: '',
  component: AuthComponent,
  children: [
    {
      path: 'login',
      component: LoginComponent,
     
    },

    {
      path: 'signup',
      component: SignupComponent,
      
    },

    {
      path: 'activate',
      component: ActivateComponent,
      
    }

  ]

}];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
