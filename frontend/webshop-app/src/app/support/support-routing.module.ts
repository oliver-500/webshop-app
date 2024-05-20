import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SupportPageComponent } from './support-page/support-page.component';
import { SupportContainerComponent } from './support-container/support-container.component';



const routes: Routes = [{
  path: '',
  component: SupportContainerComponent,
  children: [
    {
      path: '',
      component: SupportPageComponent,
    },
   
  ]
  }
];



@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupportRoutingModule { }
