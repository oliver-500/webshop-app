import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PurchaseProductRoutingModule } from './purchase-product-routing.module';
import { PurchaseProductContainerComponent } from './purchase-product-container/purchase-product-container.component';
import { PurchaseProductComponent } from './purchase-product/purchase-product.component';
import { Routes } from '@angular/router';
import { AppMaterialModule } from '../app-material/app-material.module';

import { PurchaseListComponent } from './purchase-list/purchase-list.component';



@NgModule({
  declarations: [
    PurchaseProductContainerComponent,
    PurchaseProductComponent,
    PurchaseListComponent
  ],
  imports: [
    CommonModule,
    AppMaterialModule,
    PurchaseProductRoutingModule,
   
  ],
  entryComponents: [
    PurchaseProductModule
  
  ]
 
})
export class PurchaseProductModule { }
