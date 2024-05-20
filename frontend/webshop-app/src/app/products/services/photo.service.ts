import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Photo } from 'src/app/models/photo-model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {


  private baseUrl = environment.apiUrl + "/api/v1/images";

  constructor(private http: HttpClient) {

  }


  photos?: string[];



 delete(photo : Photo){
  return this.http.delete(`${this.baseUrl}/${photo.id}`);

 }

  add(photo: Photo) {


    return this.http.post<Photo>(`${this.baseUrl}`, photo);
  }

  public getOnePhotoForProduct(id : number) {
    return this.http.get<Photo>(`${this.baseUrl}/${id}/one`);
  }



  processImage(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();

      reader.onload = (e) => {
        resolve(e?.target?.result as string);
      };

      reader.onerror = (error) => {
        reject(error);
      };

      reader.readAsDataURL(file);
    });

  }
}
