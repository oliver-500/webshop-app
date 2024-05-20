import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProductEditComponent } from './product-edit/product-edit.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { ProductsContainerComponent } from './products-container/products-container.component';


const routes: Routes = [{
  path: '',
  component: ProductsContainerComponent,
  children: [
    {
      path: 'products',
      component: ProductEditComponent,
    },
    {
      path: 'product-details',
      component: ProductDetailsComponent
    }
  ]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
