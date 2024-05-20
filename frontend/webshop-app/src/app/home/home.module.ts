import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { ProductsModule } from '../products/products.module';
import { HomeComponent } from './home/home.component';
import { AppMaterialModule } from '../app-material/app-material.module';
import { HomeContainerComponent } from './home-container/home-container.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [HomeComponent, HomeContainerComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    ProductsModule,
    AppMaterialModule
  ],

})
export class HomeModule { }
