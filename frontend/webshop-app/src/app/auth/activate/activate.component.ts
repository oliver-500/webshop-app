import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ActivationRequest } from 'src/app/models/activation-request';



import { MessageService } from 'src/app/messages/services/message.service';
import { AuthService } from '../services/auth-service.service';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent implements OnInit{

  username?: string;
  activationRequest : ActivationRequest = {};
  


  constructor(private authService : AuthService,
    private router : Router, 
    private messageService : MessageService,
    private _snackBar : MatSnackBar){
    }

    

    activate(){
      this.activationRequest.username = sessionStorage.getItem("username")!;
      
      this.activationRequest.pin = this.activationRequest.pin?.trim();
      
      this.authService.activateUser(this.activationRequest).subscribe({
        next: (token : any) => {
          if(token.token){           
            this.messageService.setMessage('Activation Succesfull!');
            this.authService.isActivationPending = false;
            sessionStorage.clear();
            localStorage.setItem("token", token.token);
            this.authService.refreshUserInfo();
            this.router.navigate(['/']);
          }
          else {
            this.messageService.setMessage("Something went wrong. Try again");
            
          }
          
          
        },
        error: (error) => {
          this.messageService.setMessage("Something went wrong. Try again");
          sessionStorage.clear();
          this.router.navigate(['/auth/login']);
        }
      }
      
      );
    }  
    

    ngOnInit(): void {

      this.messageService.getMessage().subscribe(message => {
        if (message) {
          this._snackBar.open(message, 'Dismiss', {
            duration: 3000,
          });
        }
      }); 

      if(!this.authService.isActivationPending) {
        sessionStorage.clear();
        this.router.navigate(['/auth/login']);      
        return;
      }
      this.loadUsername();
    }

    loadUsername(): void {
      const username = sessionStorage.getItem('username');
      if (username) {
        // Use the username
        this.username = username;
        
      } else {
        this.authService.isActivationPending = false;
        this.router.navigate(['/auth/login']);
      }
    }
    

}
