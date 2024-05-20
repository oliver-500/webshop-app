import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { HomeContainerComponent } from './home-container/home-container.component';

const routes: Routes = [{
  path: '',
  component: HomeContainerComponent,
  children : [
    {
      path: '',
      component: HomeComponent,
    }
  ]
 
 

}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
  

  
})
export class HomeRoutingModule { }
