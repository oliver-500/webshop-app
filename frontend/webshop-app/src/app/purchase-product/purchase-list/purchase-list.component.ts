import { Component, ViewChild } from '@angular/core';
import { PurchaseService } from '../services/purchase.service';
import { AuthService } from 'src/app/auth/services/auth-service.service';
import { ProductService } from 'src/app/products/services/product.service';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { PurchaseProduct } from 'src/app/models/purchase-model';
import { UserService } from 'src/app/users/services/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-purchase-list',
  templateUrl: './purchase-list.component.html',
  styleUrls: ['./purchase-list.component.css']
})
export class PurchaseListComponent {

  constructor(

    public purchaseService: PurchaseService,
    private authSService: AuthService,
    private productService: ProductService,
    private router: Router
  ) {
  }

  displayedColumns: string[] = ['Product', 'Price', "Paying Method"];

  @ViewChild("table") table: MatTable<any> | undefined;


  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  openProductPage(row : any){
  

    this.productService.setSelectedProduct(row.product);
    this.router.navigate(['/product-details']);
  }

  ngOnInit() {
    this.purchaseService.purchases = [];
    this.purchaseService.getPurchasesOfUser(this.authSService.currentUser?.id!).subscribe((data: any) => {

      this.dataSource = new MatTableDataSource<any>(this.purchaseService.purchases);
   
      
      data.forEach((purchase: PurchaseProduct) => {
       
        this.productService.getProductById(purchase.productId!).subscribe((data: any) => {

          purchase.product = data;
          this.purchaseService.purchases.push(purchase)
          this.table?.renderRows();

         

        });

      });

    })
  }
}
