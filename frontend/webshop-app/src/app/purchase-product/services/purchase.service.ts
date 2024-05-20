import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PurchaseProduct } from 'src/app/models/purchase-model';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  constructor(private http: HttpClient) { }

  
  private baseUrl = environment.apiUrl + "/api/v1/purchases";

  purchases : PurchaseProduct[] = [];

  

  makePurchase(p: PurchaseProduct) {
    return this.http.post<any>(`${this.baseUrl}`, p);

  }
  getPurchasesOfUser(id : number){
    return this.http.get<any>(`${this.baseUrl}/${id}/user`);
    
  }
}
