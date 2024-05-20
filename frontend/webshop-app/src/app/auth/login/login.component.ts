import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot } from '@angular/router';
import { LoginRequest } from 'src/app/models/login-request';


import { MessageService } from 'src/app/messages/services/message.service';
import { User } from 'src/app/models/user';
import { AuthService } from '../services/auth-service.service';
import { MatFormFieldControl } from '@angular/material/form-field';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],

})






export class LoginComponent implements OnInit, AfterViewInit {
  loginRequest: LoginRequest = {
    username: '',
    password: '',
  };

  
  @ViewChild('usernameInput') usernameInput: ElementRef | undefined;

  resolveFn(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {

  }

  constructor(
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService,
    private _snackBar: MatSnackBar) {
  }


  loginUser() {
    this.authService.loginUser(this.loginRequest).subscribe({
      next: (token: any) => {
        if (token) {
         
          
          this.authService.loggedIn = true;
           ;
          
          
          localStorage.setItem("token", token.token);
          this.authService.refreshUserInfo();
          this.messageService.setMessage('Successful login!');
       
          
          this.router.navigate(['/']);
        }
        else this.messageService.setMessage("Something went wrong. Try again.");
      },
      error: (error) => {

        if (error.headers == null) {

          this.messageService.setMessage("Username or password is incorrect. Try again.");


          return;
        }

        const errorMessage = error.headers.get('Errormessage');
        if (errorMessage) {
          this.messageService.setMessage(errorMessage);
          if (errorMessage == "Your account has not been activated. Please activate your account using code we sent to your email address.") {
            sessionStorage.setItem("username", this.loginRequest.username!);
            this.authService.isActivationPending = true;
            this.router.navigate(['/auth/activate']);
          }
        }
        else this.messageService.setMessage("Username or password is incorrect. Try again");


      }
    })
  }






   ngOnInit() {


  




    this.messageService.getMessage().subscribe((message: string) => {
      if (message) {
        this._snackBar.open(message, 'Dismiss', {
          duration: 3000, panelClass: ['success-snackbar']
        });
        this.messageService.setMessage("");
      }
    });
    
    if (this.authService.loggedIn == true) {
      
      
      this.router.navigate(['/']);
    }



  }

  ngAfterViewInit(){
    if (this.usernameInput) {
      this.usernameInput.nativeElement.focus();
    }
     ;
  }

  isUsernameTooShort(): boolean {


    if (this.loginRequest && this.loginRequest.username && this.loginRequest.username.length < 4) {
      return true;
    }
    return false;
  }

  isFieldTooShort(field: string | undefined, minLength: number): boolean {
    if(field == undefined) return false;
    
    return  field.length < minLength;
  }

  

}




