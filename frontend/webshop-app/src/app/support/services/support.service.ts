import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Question } from 'src/app/models/question-model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SupportService {

  

  private baseUrl = environment.apiUrl + "/api/v1/messages";

  constructor(private http: HttpClient) { 
    
  }

  public sendQuestion(question: Question) {
    return this.http.post<Question>(`${this.baseUrl}`, question);
  }
}
