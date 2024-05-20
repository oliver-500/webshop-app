import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TokenResolver } from './resolvers/TokenResolver';
import { GuardService } from './auth/services/guard.service';



const routes: Routes = [
  
  {
    path: '',    
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
    resolve: { token: TokenResolver }
  },
  {
    path: 'myaccount',    
    loadChildren: () => import('./users/users.module').then(m => m.UsersModule),
    canActivate: [GuardService],
  },

  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
    
  },

  {
    
    path: 'myshop',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
   
    canActivate: [GuardService],
    
  },

  {
    path: 'support',
    loadChildren: () => import('./support/support.module').then(m => m.SupportModule),
    canActivate: [GuardService],
    
  },
  {
    path: 'mypurchases',  
    loadChildren: () => import('./purchase-product/purchase-product.module').then(m => m.PurchaseProductModule),
    canActivate: [GuardService],
  },

  

  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
