import { Injectable } from '@angular/core';
import { AuthService } from './auth-service.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GuardService {

  constructor(private authService: AuthService,
    private router : Router) { }

    canActivate() : Observable<boolean>{

      this.authService.refreshUserInfo();
     

      return new Observable<boolean>((observer) => {
        if(this.authService.loggedIn){
          observer.next(true);
          observer.complete();
        }
        else if(!this.authService.loggedIn && localStorage.getItem('token') != null){
          this.authService.isTokenValid().subscribe({
            next: () => {
              
              this.authService.loggedIn = true;
              observer.next(true);
              observer.complete();
            },
            error: () => {
        
              this.authService.loggedIn = false;
              
              localStorage.clear();
              this.router.navigate(['/']);
              observer.next(false);
              observer.complete();
            }

          });
        }
        else{
          this.router.navigate(['/']);
          observer.next(false);
          observer.complete();
        }
       
      },
      
      );

    }
    
}
