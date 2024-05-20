import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserPanelComponent } from './user-panel/user-panel.component';
import { UserPanelContainerComponent } from './user-panel-container/user-panel-container.component';

const routes: Routes = [{
  path: '',
  component: UserPanelContainerComponent,
  children: [
    {
      path: '',
      component: UserPanelComponent,
    },
   
  ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
