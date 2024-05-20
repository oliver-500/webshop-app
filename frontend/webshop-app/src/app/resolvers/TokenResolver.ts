import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth/services/auth-service.service';





@Injectable({
  providedIn: 'root'
})
export class TokenResolver {
  constructor(
    private authService: AuthService
  ) {}

  resolve: ResolveFn<void> = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    
    return new Promise<void>((resolve, reject) => {

      
        if(!this.authService.loggedIn && localStorage.getItem('token') != null) {
         
            this.authService.isTokenValid().subscribe({
                next: () => {
                
                  
                  this.authService.loggedIn = true;
               
                
                  resolve(); // Resolve the Promise when the subscription completes
                },
                error: () => {        
                  
                  this.authService.loggedIn = false;
                 
                  localStorage.clear();
                  resolve(); // Resolve the Promise when the subscription completes
                }
              });
        }
        else {
      
          resolve();
        }
    });
  };
    
}

