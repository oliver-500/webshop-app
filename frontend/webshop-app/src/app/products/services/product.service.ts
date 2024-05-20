import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AttributeCategory } from 'src/app/models/attribute-model';
import { AttributeProduct } from 'src/app/models/attribute-product-model';
import { Category } from 'src/app/models/category-model';
import { CommentProduct } from 'src/app/models/comment';
import { Photo } from 'src/app/models/photo-model';
import { ProductCategory } from 'src/app/models/product-category';
import { Product } from 'src/app/models/product-model';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root' // This line provides the service at the root level
})
export class ProductService {

  constructor(private http: HttpClient) {
   
  };

 
  private baseUrl = environment.apiUrl + "/api/v1";

  selectedCard: Product | undefined;

  setSelectedProduct(product: Product) {
    this.selectedCard = product;
  }

  getSelectedProduct() {
    return this.selectedCard;
  }



  getCategoryByProductId(id: number) {
    return this.http.get<Category>(`${this.baseUrl}/categories/product/${id}`);
  }

  update(p: Product) {
    return this.http.put<Product>(`${this.baseUrl}/products/${p.id}`, p);
  }

  getProductById(id: number) {
    return this.http.get<Product>(`${this.baseUrl}/products/${id}`);

  }

  deleteProductAttribute(ap: AttributeProduct) {

    return this.http.delete<any>(`${this.baseUrl}/attributes/${ap.productId}/${ap.attributeId}`);
  }

  deleteProductCategory(pc: ProductCategory) {
    return this.http.delete<any>(`${this.baseUrl}/categories/${pc.productId}/${pc.categoryId}`);
  }


  lowPrice: string = "";
  keyword: string = "";
  highPrice: string = "";
  selectedCategory: string = "All";
  selectedCondition: string = "All";
  products: Product[] = [];

  categories: Category[] = [];
  conditions: string[] = ["New", "Used"];
  
  public add(product: Product) {
    return this.http.post<Product>(`${this.baseUrl}/products`, product);

  }



  public addCategoryForProduct(productCategory: ProductCategory) {
    return this.http.post<Product>(`${this.baseUrl}/products/addCategory`, productCategory);
  }


  public filterProducts(category: string, condition: string, keyword: string, lowPrice: string, highPrice: string, userId: string) {
    const params = new HttpParams()
      .set('user', userId)
      .set('condition', condition)
      .set('category', category)
      .set('lowprice', lowPrice)
      .set('highPrice', highPrice)
      .set('keyword', keyword);


    return this.http.get<Product>(`${this.baseUrl}/products/search`, { params });
  }

  public getAttributesForProductId(id: number) {
    return this.http.get<AttributeProduct>(`${this.baseUrl}/attributes/product/${id}`);

  }

  public addAttribute(attribute: AttributeProduct) {
    return this.http.post<Product>(`${this.baseUrl}/products/setAttribute`, attribute);

  }

  public deleteProduct(id: number) {
    return this.http.delete<any>(`${this.baseUrl}/products/${id}`);

  }

  getProductsOfUser(id: number) {
    return this.http.get<Product>(`${this.baseUrl}/products/user/${id}`);

  }

  getAttributes(selectedCategory: any) {
    return this.http.get<AttributeCategory>(`${this.baseUrl}/categories/${selectedCategory.id}/attributes`);
  }

  getProductsOfCategory(value: any) {
    if (value == "All") {

      return this.http.get<Product>(`${this.baseUrl}/products`);
    }

    return this.http.get<Product>(`${this.baseUrl}/products/category/${value}`);
  }


 
  public getAllProducts() {
    return this.http.get<Product>(`${this.baseUrl}/products`);
  }


  public getAllCategories() {
    return this.http.get<Category>(`${this.baseUrl}/categories`);
  }

  public getImagesForProduct(id: number) {
    return this.http.get<Photo>(`${this.baseUrl}/products/${id}/images`);
  }


  public updateAttributeValues(value : AttributeProduct){
    return this.http.put<Product>(`${this.baseUrl}/products/updateAttribute`, value);
  }


}

