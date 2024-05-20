import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Product } from 'src/app/models/product-model';
import { PurchaseProduct } from 'src/app/models/purchase-model';
import { PurchaseService } from '../services/purchase.service';
import { MessageService } from 'src/app/messages/services/message.service';
import { AuthService } from 'src/app/auth/services/auth-service.service';

@Component({
  selector: 'app-purchase-product',
  templateUrl:  './purchase-product.component.html',
  styleUrls: ['./purchase-product.component.css']
})
export class PurchaseProductComponent {
  constructor(
    private dialogRef: MatDialogRef<PurchaseProductComponent>,
    @Inject(MAT_DIALOG_DATA) data: any,
    private formBuilder : FormBuilder,
    private authService : AuthService,
    private purchaseService : PurchaseService,
    private messageService : MessageService) {
      this.product = data.product; 
     
      this.form = this.formBuilder.group({
        payingMethod : [this.selectedPayingOption, Validators.required],
        payingInfo : ["", Validators.required]
      });
    }

  product : Product = {}

  selectedPayingOption : string = "";
  payingInfoTitle: string = "Paying info";

  purchaseProduct : PurchaseProduct = {
    
  }

  payingOptions = ["Pay by card", "Pay on delivery"];


  form : FormGroup;
  ngOnInit() {
   

  }

  close() {
    this.dialogRef.close(false);
  }

  onMethodChanged(event: any){
    this.selectedPayingOption = event.value;
    if(this.selectedPayingOption == "Pay by card")
      this.payingInfoTitle = "Card number";
    else
      this.payingInfoTitle = "Delivery address";

  }

  confirm() {
    this.purchaseProduct.productId = this.product.id;
    if(this.selectedPayingOption == "Pay by card"){
      this.purchaseProduct.payingMethod = "card";
    }
    else this.purchaseProduct.payingMethod = "delivery";
   
    this.purchaseProduct.payingInfo = this.form.value.payingInfo;
    this.purchaseProduct.traderId = -1;
     ;

    this.purchaseService.makePurchase(this.purchaseProduct).subscribe((data : any) =>{
      this.messageService.setMessage("Purchase succesfully made.")
      this.dialogRef.close(true);
    });
    
  }
}
