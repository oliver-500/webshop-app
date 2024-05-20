import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';



import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';

import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { AppMaterialModule } from './app-material/app-material.module';
import { GuestpanelComponent } from './pages/guestpanel/guestpanel.component';
import { ConfirmModalComponent } from './pages/confirm-modal/confirm-modal.component';

import { SupportPageComponent } from './support/support-page/support-page.component';
import { PurchaseProduct } from './models/purchase-model';
import { PurchaseProductComponent } from './purchase-product/purchase-product/purchase-product.component';
import { AuthService } from './auth/services/auth-service.service';








@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    GuestpanelComponent,
    ConfirmModalComponent
    
    

  ],
  imports: [
    AppMaterialModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,  
  ],
  providers: [
    HttpClient, 
    AuthService,
      {
        provide: HTTP_INTERCEPTORS,
        useClass: AuthService,
        multi: true,
      },
  
  ],
  
  bootstrap: [AppComponent],
  entryComponents: [
    ConfirmModalComponent
  
  ]
})
export class AppModule { }
