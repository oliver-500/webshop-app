import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth-service.service';

import { MessageService } from 'src/app/messages/services/message.service';


@Component({
  selector: 'app-guestpanel',
  templateUrl: './guestpanel.component.html',
  styleUrls: ['./guestpanel.component.css']
})
export class GuestpanelComponent {
    constructor(public authService : AuthService, 
      private messageService : MessageService, private router : Router){

    }

  
  handleHomeClick(): void {
    if(! (this.router.url == "/")) this.router.navigate(['/']);
  }
    
  logoutUser(): void {
    localStorage.removeItem('token');
    this.authService.loggedIn = false;
    this.authService.currentUser = null;
    this.router.navigate(['/']);
    this.messageService.setMessage("Successfully logged out.");
     ;
  }

  isNotHomePage() : boolean {
    //check if page is sign-up or login
    if(this.router.url.includes('login') || this.router.url.includes('signup') || this.router.url.includes('activate')) return true;
    else return false;
  
  }

  goToLoginPage() : void {
    this.router.navigate(['auth/login']);
  }

  ngOnInit() {
    this.authService.refreshUserInfo();
   
  }

  
  

}

  

