import { Component, Inject, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { Product } from 'src/app/models/product-model';
import { ProductService } from '../services/product.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Category } from 'src/app/models/category-model';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PhotoService } from '../services/photo.service';
import { AttributeProduct } from 'src/app/models/attribute-product-model';

import { User } from 'src/app/models/user';
import { Attribute } from 'src/app/models/attribute-raw';
import { ProductCategory } from 'src/app/models/product-category';
import { MessageService } from 'src/app/messages/services/message.service';
import { Photo } from 'src/app/models/photo-model';
import { AuthService } from 'src/app/auth/services/auth-service.service';



@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent {

  public form: FormGroup = new FormGroup({}); //forma
  public product: Product = new Product();
  public formBuilder: FormBuilder = new FormBuilder();

  constructor(
    public productService: ProductService,
    private dialogRef: MatDialogRef<ProductEditComponent>,
    private snackBar: MatSnackBar,
    private messageService: MessageService,
    public photoService: PhotoService,
    public authService: AuthService,
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.isEdit = data.isEdit;
    this.product = data.product;

  }

  isEdit = false;
  currentStep: number = 1;
  categoriesList?: Category[];
  selectedCategory?: Category;
  conditionsList?: string[] = ["New", "Used"];

  attributes: Attribute[] = [];

  oldProduct: Product = new Product();
  oldSelectedCategory: Category | null = null;
  oldPhotos: Photo[] = [];
  oldAttributes: AttributeProduct[] = [];
  newAttributes: AttributeProduct[] = [];
  photos: Photo[] = [];

  fieldArray: FormArray = this.formBuilder.array([]);

  nextStep(): void {
    this.currentStep = 2;
  }

  previousStep(): void {
    this.currentStep = 1;
  }

  onCategoryChange(event: any) {

    if (this.isEdit) {
      this.isEdited = true;
    }

    this.fieldArray.clear();
    this.attributes = [];
    this.selectedCategory = event.value;

    this.productService.getAttributes(this.selectedCategory).subscribe((data: any) => {
      for (const item of data) {
        this.attributes.push(item);
        const newControl = this.formBuilder.control("", Validators.required);
        this.fieldArray.push(newControl);
      }
    });
  }


  ngOnInit(): void {
    if (this.product == null) {
      this.product = {
        title: "adda",
        description: "adda",
        price: "34",
        contact: "adda",
        used: false,
        location: "adda",

      }
    }



    this.productService.getAllCategories().subscribe((data: any) => {
      this.categoriesList = data;

    });

    this.form = this.formBuilder.group({
      title: [this.product.title, [Validators.required, Validators.maxLength(50)]],
      description: [this.product.description, Validators.required],
      price: [this.product.price, [Validators.required, Validators.pattern(/^\d+/), Validators.maxLength(9)]],
      contact: [this.product.contact, Validators.required],
      used: ["", Validators.required],
      location: [this.product.location, Validators.required],
      category: [this.selectedCategory, Validators.required],
      fieldArray: this.fieldArray

    });
      
    if (this.isEdit == true) {

      this.oldProduct = this.product;

      var selectedValues;
      selectedValues = this.product.used ? "Used" : "New";

      this.form.get("used")?.setValue(selectedValues);

      this.productService.getCategoryByProductId(this.product.id!).subscribe((data: Category) => {

        for (let i = 0; i < this.categoriesList!.length; i++) {
          if (this.categoriesList![i].id == data.id) {
            this.selectedCategory = this.categoriesList![i];

            this.form.get('category')?.setValue(this.categoriesList![i]);

            this.oldSelectedCategory = this.selectedCategory;


            this.productService.getAttributes(this.selectedCategory).subscribe((data: any) => {

              this.productService.getAttributesForProductId(this.product.id!).subscribe((attributes: any) => {
                this.oldAttributes = attributes;
                this.newAttributes = attributes;
                let i = 0;
                for (const item of data) {

                  
                  this.attributes.push(item);
                  let newControl;
                  if(this.oldAttributes.length  > i){
                    
                    newControl = this.formBuilder.control(this.oldAttributes[i].value, Validators.required);
                      
                  }
                  else {
                      
                    newControl = this.formBuilder.control("", Validators.required);
                  }
                  this.fieldArray.push(newControl);
                  i++;

                }
              })



            });

            this.productService.getImagesForProduct(this.product.id!).subscribe((data: any) => {
              this.photos = data;
              if (this.isEdit) data.forEach((p: Photo) => this.oldPhotos.push(p));
            })



            break;
          }
        }
      });






    }
    else {

      if (this.product == null || this.product == undefined)
        this.product = new Product();
      this.product = {
        title: "adda",
        description: "adda",
        price: "34",
        contact: "adda",
        used: false,
        location: "adda",

      }
    }



  }
  get getFieldArray(): FormArray {
    return this.form.get('fieldArray') as FormArray;
  }

  close() {
    this.dialogRef.close(false);
  }

  removePhoto(index: number) {
    this.photos = this.photos?.filter((_, i) => i !== index);
    this.photoInput.nativeElement.value = null;
    this.isEdited = true;
  }

  isEdited: boolean = false;

  isFormInvalid(): boolean {
    if (!this.isEdit) {
        
      return this.form.invalid;
    }
    else {
      if (this.isEdited) {
          
        return this.form.invalid;
      }
      else {
          
        return !(this.checkIfChanged() || this.checkIfAttributesChanged() || this.checkIfPhotosChanged());


      }
    }
  }

  checkIfChanged() {
    if (this.oldProduct.title != this.form.value.title) {
        
      return true;

    }
    if (this.oldProduct.description != this.form.value.description) {
        
      return true;

    }
    if (this.oldProduct.price != this.form.value.price) {
        
      return true;

    }
    if (this.oldProduct.contact != this.form.value.contact) {
        
      return true;

    }
    if (this.oldProduct.location != this.form.value.location) {
        
      return true;

    }
    if (this.oldProduct.used ? "Used" : "New" != this.form.value.used) {
        
      return true;

    }
      
    return false;
  }

  checkIfAttributesChanged(): boolean {
      
    if (this.oldSelectedCategory != this.selectedCategory) {

      return true;

    }
    for (let i = 0; i < this.attributes.length; i++) {
      if(i == this.oldAttributes.length){
        if(this.form.value.fieldArray[i].length > 0) return true;
        else return false;
      }
      if (this.oldAttributes[i].value != this.form.value.fieldArray[i]) return true;
    }

    return false;
  }

  arePhotosChanged: boolean = false;

  checkIfPhotosChanged(): boolean {
     
    if (!this.arePhotosChanged) {
       
      if (this.photos?.length != this.oldPhotos.length) this.arePhotosChanged = true;
      else {
         
         
        for (let i = 0; i < this.photos?.length; i++) {
          if (this.photos[i] != this.oldPhotos[i]) {
            this.arePhotosChanged = true;
             
             
            return true;
          }

        }
      }
    }
     
    return this.arePhotosChanged;
  }

  isButtonDisabled = false;

  updateAttributes(p: Product, isValueUpdating: boolean) {
    this.newAttributes = [];
    for (let i = 0; i < this.attributes.length; i++) {

      const attribute: AttributeProduct = new AttributeProduct();
      var arrayControl = this.form.get('fieldArray') as FormArray;
      var item = arrayControl.at(i);
      attribute.value = item.value;
      attribute.productId = p.id;
      attribute.attributeId = this.attributes[i].id;
      this.newAttributes.push(attribute);
    }
    if (isValueUpdating == false) {
      for (let i = 0; i < this.oldAttributes.length; i++) {
          
        this.productService.deleteProductAttribute(this.oldAttributes[i]).subscribe((data: any) => {

        });

      }

      for (let i = 0; i < this.newAttributes.length; i++) {
          
        this.productService.addAttribute(this.newAttributes[i]).subscribe((data: Product) => {
        });

      }
    }

    else {
      for (let i = 0; i < this.newAttributes.length; i++) {
        if(i >= this.oldAttributes.length){
          this.productService.addAttribute(this.newAttributes[i]).subscribe((data: any) =>{
          
          })
        }
        else
         
        this.productService.updateAttributeValues(this.newAttributes[i]).subscribe((data: Product) =>{
          
        })
      }
      
    }



  }

  updatePhotos() {
    if (this.checkIfPhotosChanged()) {
      //za brisanje
      this.oldPhotos.filter((o: Photo) => {

        return !this.photos.includes(o);

      }).forEach((o: Photo) => {
        this.photoService.delete(o).subscribe((data: any) => {
        })

      });

      //za dodavanje
      this.photos.filter((o: Photo) => {
        return !this.oldPhotos.includes(o);
      });

      if (this.photos.length == 0) this.dialogRef.close(false);

      this.photos.forEach((p: Photo) => {
        this.photoService.add(p).subscribe((data: any) => {
          this.dialogRef.close(true);
        });
      });

    }
  }




  updateeAttributes(p: Product) {
    if (this.checkIfAttributesChanged()) {

      if (this.oldSelectedCategory == this.selectedCategory) {

        this.updateAttributes(p, true);
      }
      else {
        //update kategoriju i atribute
        var productCategory: ProductCategory = new ProductCategory();
        productCategory.productId = p.id;
        productCategory.categoryId = this.oldSelectedCategory?.id;
         
        this.productService.deleteProductCategory(productCategory).subscribe((data: any) => {
          productCategory.categoryId = this.selectedCategory?.id;
           
          this.productService.addCategoryForProduct(productCategory).subscribe((data: any) => {
            this.updateAttributes(p, false);
          })

        });



      }
    }
  }

  save({ value, valid }: { value: Product, valid: boolean }) {


    if (valid) {



      if (this.photos.length == 0) {
        this.snackBar.open("Please add at least one photo of your product", 'Dismiss', {
          duration: 3000,
        });
        return;
      }

      value.price = value.price?.toString().replace("€", "");
      value.price = value.price?.replace(".", "");

      value.price = value.price?.replace(",", ".");

      value.sold = false;

      var p: Product = this.form.value;
      p.sellerId = this.authService.currentUser?.id;
      p.used = this.form.value.used == "Used" ? true : false;
      p.id = this.product.id;

      if (this.isEdit == true) {

        p.categoryId = this.selectedCategory?.id;

        if (this.checkIfChanged()) {
           
          this.productService.update(p).subscribe((data: Product) => {

            this.updateeAttributes(p);


            this.updatePhotos();

            this.product = data;
            this.snackBar.open("Product succesfully updated", 'Dismiss', {
              duration: 3000,
            });
            this.dialogRef.close(true);

          })
        }
        else {
          this.updateeAttributes(p);


          this.updatePhotos();
          this.dialogRef.close(true);
        }






      }

      else {


        this.productService.add(p).subscribe((data: Product) => {

          for (let i = 0; i < this.attributes.length; i++) {

            const attribute: AttributeProduct = new AttributeProduct();
            var arrayControl = this.form.get('fieldArray') as FormArray;
            var item = arrayControl.at(i);
            attribute.value = item.value;
            attribute.productId = data.id;
            attribute.attributeId = this.attributes[i].id;
            this.productService.addAttribute(attribute).subscribe((data2: any) => {

            });


          }


          const productCategory: ProductCategory = new ProductCategory();
          productCategory.productId = data.id;

          productCategory.categoryId = this.selectedCategory?.id;


          this.productService.addCategoryForProduct(productCategory).subscribe((data: any) => {

          });

          for (let i = 0; i < this.photos.length; i++) {

            const photo: Photo = this.photos[i];

            photo.productId = data.id;




            this.photoService.add(photo).subscribe((data3: any) => {

              this.form.reset();

              this.dialogRef.close(true);

            });
          }




        })

      }

    }














  }



  onInputBlur() {
    const priceControl = this.form.get('price');

    if (priceControl?.value == null) return;
    if ((priceControl?.value.length > 0 && !priceControl?.value.toString().endsWith("€"))) {

      priceControl?.setValue(priceControl.value + "€");
    }
  }

  onInputFocus() {
    ;
    const priceControl = this.form.get('price');
    if (priceControl?.value == null) return;
    if (priceControl?.value.toString().endsWith("€")) {
      priceControl?.setValue(priceControl.value.slice(0, -1));
    }

  }




  @ViewChild('photoInput') photoInput: any;
  //numberOfImages: number = 0;

  onCardClick(event: any) {

  }



  //imageUrls: string[] = [];
  onFilesSelected(event: any) {

    const files: FileList = event.target.files;

    if (files.length + this.photos.length > 10) {
      this.snackBar.open("No more than 10 photos allowed", undefined, { //i prikazemo poruku koja nestaje nakon 2s
        duration: 2000,
      });
      return;
    }


    for (let i = 0; i < files.length; i++) {

      if (this.photos.length > 9) return;
      this.photoService.processImage(files[i]).then((resultString: string) => {
        var p: Photo = new Photo();
        p.data = resultString;
        p.productId = this.product.id;
        this.photos.push(p);

      })
        .catch((error) => {

        });


    }

  }










}
