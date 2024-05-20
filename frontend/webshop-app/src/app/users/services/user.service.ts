import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ChangePasswordRequest } from 'src/app/models/update-password-request';
import { User } from 'src/app/models/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  

  private baseUrl = environment.apiUrl + "/api/v1/users";

  constructor(private http: HttpClient) {

  }

  public updatePassword(request : ChangePasswordRequest, id : number){
    return this.http.put(`${this.baseUrl}/${id}/changePassword`, request);
  
  }

  public updateUser(request : User, id : number){
    return this.http.put(`${this.baseUrl}/${id}`, request);
  
  }

  public findAvatar(username : string){
    return this.http.get<User>(`${this.baseUrl}/avatar/${username}`);
  
  }

  public findById(id : number){
    return this.http.get<User>(`${this.baseUrl}/username/${id}`);
  
  }

  
}
