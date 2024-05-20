import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SupportRoutingModule } from './support-routing.module';
import { SupportService } from './services/support.service';
import { SupportContainerComponent } from './support-container/support-container.component';
import { AppModule } from '../app.module';
import { AppMaterialModule } from '../app-material/app-material.module';
import { SupportPageComponent } from './support-page/support-page.component';


@NgModule({
  declarations: [
    SupportContainerComponent,
    SupportPageComponent
    
  ],
  imports: [
    CommonModule,
    SupportRoutingModule,
    AppMaterialModule
  ],
  providers: [SupportService]
})
export class SupportModule { }
