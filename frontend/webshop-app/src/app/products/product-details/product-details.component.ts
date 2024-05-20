import { Attribute } from '@angular/compiler';
import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';

import { AttributeCategory } from 'src/app/models/attribute-model';
import { AttributeProduct } from 'src/app/models/attribute-product-model';
import { Category } from 'src/app/models/category-model';
import { CommentProduct } from 'src/app/models/comment';
import { Photo } from 'src/app/models/photo-model';
import { Product } from 'src/app/models/product-model';
import { User } from 'src/app/models/user';
import { ProductService } from 'src/app/products/services/product.service';
import { UserService } from 'src/app/users/services/user.service';
import { CommentService } from '../services/comment.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PurchaseProduct } from 'src/app/models/purchase-model';
import { PurchaseProductComponent } from 'src/app/purchase-product/purchase-product/purchase-product.component';
import { AuthService } from 'src/app/auth/services/auth-service.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {

  constructor(
    public productService: ProductService,
    public commentService: CommentService,
    private router: Router,
    private formBuilder: FormBuilder,
    public userService: UserService,
    public authService: AuthService,
    private dialog: MatDialog) {
  }

  product?: Product | null = null;

  category?: Category | null = null;

  images: string[] = [];

  attributes: AttributeProduct[] = [];

  attributesOfCategory: AttributeCategory[] = [];

  photos: Photo[] = [];

  selectedImageIndex?: number | null = null;

  changeIndex(right: boolean) {
    if (right) this.selectedImageIndex!++;
    else this.selectedImageIndex!--;
    if (this.selectedImageIndex! < 0) this.selectedImageIndex = this.images!.length - 1;
    if (this.selectedImageIndex! > this.images!.length - 1) this.selectedImageIndex = 0;
    ;
  }



  ngOnInit() {

    this.product = this.productService.getSelectedProduct();

     

    if (this.product == null || this.product == undefined) {

      this.router.navigate(['/']);
      return;
    }
    if (this.productService.selectedCategory == "All") {
      this.productService.getCategoryByProductId(this.product?.id!).subscribe((data: any) => {
        this.category = data;


        this.productService.getAttributesForProductId(this.product?.id!).subscribe((attributes: any) => {
          this.attributes = attributes;
        })
        this.productService.getAttributes(this.category).subscribe((attributes: any) => {
          this.attributesOfCategory = attributes;

        });

      });
    }
    else {

      for (let i = 0; i < this.productService.categories.length; i++) {
        if (this.productService.categories[i].id == parseInt(this.productService.selectedCategory, 10)) {
          this.category = this.productService.categories[i]
        }
      }
      this.productService.getAttributesForProductId(this.product?.id!).subscribe((attributes: any) => {
        this.attributes = attributes;
      })
      this.productService.getAttributes(this.productService.selectedCategory).subscribe((attributes: any) => {
        this.attributesOfCategory = attributes;

      });





    }


    this.commentService.getAllCommentsForPRoduct(this.product?.id!).subscribe((data: any) => {
      this.commentService.comments = data;
      this.commentService.comments.reverse();
      this.commentService.comments.forEach((product: CommentProduct) => {
        this.userService.findAvatar(product.username!).subscribe((data: User) => {
          if (data.avatar == null) product.userAvatar = undefined
          else product.userAvatar = data.avatar;

        },
          (error) => {


          })
      });

    });



    this.productService.getImagesForProduct(this.product?.id!).subscribe((data: any) => {

      this.photos = data;
      this.photos?.map((photo: Photo) => {

        this.images?.push(photo.data!);
        return photo;

      });


      if (this.images!.length > 0) this.selectedImageIndex = 0;

    });


  }

  isCommenting: boolean = false;
  form!: FormGroup;
  question: CommentProduct | null = null;

  writeComment() {
    this.question = new CommentProduct();
    this.form = this.formBuilder.group({
      mcontent: [this.question.mcontent, [Validators.required]],
    });
    this.isCommenting = true;
    const textarea = document.getElementById('comment-area');
    if (textarea) {
      textarea.focus();
    }
  }

  addComment() {
    if (this.question == null) this.question = new CommentProduct();
    this.question.mcontent = this.form.value.mcontent;
    this.question.username = this.authService.currentUser?.username;
    this.question.productId = this.product?.id!;
    this.commentService.addComment(this.question).subscribe((data: any) => {
      this.commentService.comments.unshift(data);
      this.question = null;
    });
    this.isCommenting = false;

  }

  buyProduct(event: MouseEvent){
    event.stopPropagation();

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.restoreFocus = false;
    dialogConfig.width = "600px";
  
    dialogConfig.data = {
      product: this.product
    }

    this.dialog.open(PurchaseProductComponent, dialogConfig)
      .afterClosed()
      .subscribe(result => {
        if(result )
          this.router.navigate(['/mypurchases']);
      });
  }

  isOwnProduct(){
     ;
     
    return this.product?.sellerId == this.authService.currentUser?.id;
  }

}


