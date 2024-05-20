import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PurchaseProductComponent } from './purchase-product/purchase-product.component';
import { PurchaseProductContainerComponent } from './purchase-product-container/purchase-product-container.component';
import { PurchaseListComponent } from './purchase-list/purchase-list.component';


const routes: Routes = [{
  path: '',
  component: PurchaseProductContainerComponent,
  children: [
    
    {
      path: '',
      component: PurchaseListComponent
    }
  ]
  },
  
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PurchaseProductRoutingModule { }
