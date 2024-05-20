import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductsRoutingModule } from './products-routing.module';
import { ProductEditComponent } from './product-edit/product-edit.component';
import { ProductService } from './services/product.service';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { AppMaterialModule } from '../app-material/app-material.module';
import { ProductsContainerComponent } from './products-container/products-container.component';



@NgModule({
  declarations: [
 
    ProductEditComponent,
    ProductDetailsComponent,
    ProductsContainerComponent,
    
  ],
  imports: [
    CommonModule,
    ProductsRoutingModule,
    AppMaterialModule
  ],

})
export class ProductsModule { }
