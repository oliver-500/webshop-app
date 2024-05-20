import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommentProduct } from 'src/app/models/comment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }


  private baseUrl = environment.apiUrl + "/api/v1/comments";

  comments : CommentProduct[] = [];

  public getAllCommentsForPRoduct(id : number){
    return this.http.get<Comment>(`${this.baseUrl}/${id}`);
  
  }

  public addComment(comment : CommentProduct){
    return this.http.post<Comment>(`${this.baseUrl}`, comment);
  }
}
