import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSelectChange } from '@angular/material/select';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth-service.service';

import { MessageService } from 'src/app/messages/services/message.service';
import { Photo } from 'src/app/models/photo-model';
import { Product } from 'src/app/models/product-model';
import { ConfirmModalComponent } from 'src/app/pages/confirm-modal/confirm-modal.component';
import { ProductEditComponent } from 'src/app/products/product-edit/product-edit.component';
import { PhotoService } from 'src/app/products/services/photo.service';
import { ProductService } from 'src/app/products/services/product.service';
import { PurchaseService } from 'src/app/purchase-product/services/purchase.service';




@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {



  onCardClick(product: Product) {
    this.productService.setSelectedProduct(product);
    this.router.navigate(['/product-details']);
  }

  handlePageEvent(e: PageEvent) {

  }

  onPageChange(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.page = e.pageIndex + 1;


  }




  length?: number;
  pageSize: number = 50; // Number of items per page
  pageSizeOptions: number[] = [5, 10, 25, 50]; // Page size options
  page: number = 1; // Current page

  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent | undefined;


  sideNavOpened: boolean = false;

  filter() {


    var category = "";
    var condition = "";
    var keyword = this.productService.keyword;
    var lowPrice = this.productService.lowPrice;
    var highPrice = this.productService.highPrice;
    var user = "";
    if (this.showOnlyUserProducts) user = this.authService.currentUser?.username!;
    if (this.productService.selectedCategory != "All") category = this.productService.selectedCategory;
    if (this.productService.selectedCondition != "All") condition = this.productService.selectedCondition;

    this.productService.filterProducts(category, condition, keyword, lowPrice, highPrice, user).subscribe((data: any) => {
      this.productService.products = data;
      this.page = 1;
      this.length = this.productService.products?.length;

      this.productService.products.forEach((product: Product) => {
        this.loadThumbnails();
      });


    }
    )
  }





  showOnlyUserProducts?: boolean;

  categories = new FormControl('');





  constructor(
    private dialog: MatDialog,
    public authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private messageService: MessageService,
    public productService: ProductService,
    public photoService: PhotoService,
    public purchaseService: PurchaseService

  ) {
  }

  delete(product: Product, event: MouseEvent) {
    event.stopPropagation();
    this.dialog.open(ConfirmModalComponent, {
      width: '300px'
    })
      .afterClosed()
      .subscribe(result => {
        if (result) {
           ;
           
          this.productService.deleteProduct(product.id!).subscribe((data: any) => {
            this.productService.getProductsOfUser(this.authService.currentUser?.id!).subscribe((data: any) => {

              this.productService.products = data;
              this.loadThumbnails();
            });
          });

        }
      });
  }

  edit(product: Product, event: MouseEvent) {
    event.stopPropagation();

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.restoreFocus = false;
    dialogConfig.width = "600px";

    dialogConfig.data = {
      isEdit: true,
      product: product
    }
    this.dialog.open(ProductEditComponent, dialogConfig)
      .afterClosed()
      .subscribe(result => {

        if (result == true) {

          this.productService.getProductsOfUser(this.authService.currentUser?.id!).subscribe((data: any) => {
            this.productService.products = data;
            this.loadThumbnails();
          }
          );
        }

      });
  }



  showAll() {
    this.productService.selectedCategory = "All";
    this.productService.selectedCondition = "All";
    this.productService.keyword = "";
    this.productService.lowPrice = "";
    this.productService.highPrice = "";
    this.loadProducts();
  }

  areFiltersShown: boolean = false;
  showFilters() {
    this.sideNavOpened = !this.sideNavOpened
    //this.areFiltersShown = !this.areFiltersShown
  }

  onEnterPressed() {
    this.filter();
  }

  add() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.restoreFocus = false;
    dialogConfig.width = "600px";
    dialogConfig.data = {
      isEdit: false,

    }
    this.dialog.open(ProductEditComponent, dialogConfig)
      .afterClosed()
      .subscribe(result => {
        if (result == true) {
          this.productService.getProductsOfUser(this.authService.currentUser?.id!).subscribe((data: any) => {
            this.productService.products = data;
            this.loadThumbnails();
          }
          );
        }

      });



  }

  ngOnDestroy(): void {
    this.productService.products = [];
  }


  ngOnInit(): void {

    const currentUrl = this.router.url;




    if (currentUrl == "/myshop") {
      this.showOnlyUserProducts = true;

    }
    else this.showOnlyUserProducts = false;

    this.messageService.getMessage().subscribe(message => {
      if (message) {
        const config = new MatSnackBarConfig();
        config.panelClass = ['custom-snackbar']; // Custom CSS class for styling
        config.horizontalPosition = 'center'; // Horizontal position: 'start', 'center', or 'end'
        config.verticalPosition = 'bottom'; // Vertical position: 'top' or 'bottom'
        config.duration = 3000; // Display duration in milliseconds
        config.panelClass = ['custom-snackbar']; // Custom CSS class for styling
        this._snackBar.open(message, 'Dismiss', config);
        this.messageService.setMessage("");

      }

    });

    this.productService.getAllCategories().subscribe((data: any) => {
      this.productService.categories = data;

    });


    if (this.productService.selectedCategory != "All" || this.productService.selectedCondition != "All"
      || this.productService.lowPrice != "" || this.productService.highPrice != "" || this.productService.keyword != "")
      this.filter();
    else
      this.loadProducts()








  }

  loadProducts() {





    if (this.showOnlyUserProducts) {
      this.productService.getProductsOfUser(this.authService.currentUser?.id!).subscribe((data: any) => {
        this.productService.products = data.reverse();




        this.length = this.productService.products?.length;
        this.productService.products.forEach((product: Product) => {
          this.loadThumbnails();
        });
      });
    }
    else {

      this.productService.getAllProducts().subscribe((data: any) => {
        this.productService.products = data;

        this.length = this.productService.products?.length;
        this.loadThumbnails();


      });

    }




  }

  loadThumbnails() {

    this.productService.products.forEach((product: Product) => {
      this.photoService.getOnePhotoForProduct(product.id!).subscribe((data: Photo) => {
        product.image = data.data;

      },
        (error) => {

        });
    });
  }



}
