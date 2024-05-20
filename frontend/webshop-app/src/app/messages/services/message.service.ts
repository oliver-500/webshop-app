import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private messageSubject = new BehaviorSubject<string>('');
  
  setMessage(message: string) {
    this.messageSubject.next(message);
  }

  getMessage() {
    return this.messageSubject.asObservable();
  }

  constructor() { }
}
