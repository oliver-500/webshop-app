import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SignUpRequest } from 'src/app/models/sign-up-request';
import { User } from 'src/app/models/user';

import { of } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { MatSnackBar } from '@angular/material/snack-bar';

import { MessageService } from 'src/app/messages/services/message.service';
import { PhotoService } from 'src/app/products/services/photo.service';
import { AuthService } from '../services/auth-service.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  selectedFile?: File;

  
  
  signUpRequest: SignUpRequest = {};
  imageUrl : string | null = null;
  
  form: FormGroup;



  constructor(
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService,
    private _snackBar: MatSnackBar,
    private formBuilder: FormBuilder,
    private photoService: PhotoService
  ) {

    this.form = this.formBuilder.group({
      email: [this.signUpRequest.email, [Validators.required, Validators.email]],
      username: [this.signUpRequest.username, Validators.required],
      password: [this.signUpRequest.password, Validators.required],
      name: [this.signUpRequest.name, Validators.required],
      lastName: [this.signUpRequest.lastName, Validators.required],
      city: [this.signUpRequest.city, Validators.required]
    });

  }



  signUpUser(value : SignUpRequest) {
    
    this.signUpRequest = value;
    this.signUpRequest.avatar = this.imageUrl!;
     ;
    this.authService.signup(this.signUpRequest).subscribe({
      next: (response: User) => {
        if (response && response.username != null) {
          sessionStorage.setItem("username", response.username);
          this.authService.isActivationPending = true;
          this.messageService.setMessage("Signing up sucessfull!");
          this.router.navigate(['/auth/activate']);

        } else {
          this.messageService.setMessage('Something went wrong. Try again');
        }
      },
      error: (error) => {
        if (error.headers == null) {
          this.messageService.setMessage("Something went wrong. Try again.");
          return;
        }
        const errorMessage = error.headers.get('Errormessage');
        if (errorMessage) {
          this.messageService.setMessage(errorMessage);
        }
        else this.messageService.setMessage("Something went wrong. Try again.");
      },
    }
    )
  }




  ngOnInit(): void {



    this.messageService.getMessage().subscribe(message => {
      if (message) {
        this._snackBar.open(message, 'Dismiss', {
          duration: 3000, panelClass: ['success-snackbar']
        });
      }
    });
    if (this.authService.loggedIn) this.router.navigate(['/']);


  }

  save({ value, valid }: { value: SignUpRequest, valid: boolean }) {
    if (valid) { //ako su OK

      // this.service.add(value);//koristimo nas servis da ih sacuvamo
      // this.form.reset(); //ponistimo prethodno unesene podatke
      // this.snackBar.open("Podaci su sacuvani", undefined, { //i prikazemo poruku koja nestaje nakon 2s
      //   duration: 2000,
      // });
      this.signUpUser(value);
    }
  }

  imageSelected : boolean | undefined;
  

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    this.photoService.processImage(this.selectedFile!).then((resultString) => {
      this.imageSelected = true;
       
    
      this.imageUrl = resultString;
     
    })
    .catch((error) => {
       
      console.error(error);
    });
    
  }


}

