import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Question } from 'src/app/models/question-model';
import { SupportService } from '../services/support.service';
import { MessageService } from 'src/app/messages/services/message.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/services/auth-service.service';

@Component({
  selector: 'app-support-page',
  templateUrl: './support-page.component.html',
  styleUrls: ['./support-page.component.css']
})
export class SupportPageComponent {

  form: FormGroup;

  question : Question = new Question()

  constructor(private formBuilder: FormBuilder,
    private authService: AuthService,
    private supportService: SupportService,
    private messageService: MessageService,
    private _snackBar: MatSnackBar) { 
    this.form = this.formBuilder.group({
      mcontent: [this.question.mcontent, [Validators.required]],
    });
  }

  send(){
    
    this.question.mcontent = this.form.value.mcontent;
    this.question.userId = this.authService.currentUser?.id;
    this.supportService.sendQuestion(this.question).subscribe((data : any)=>{
      
      this._snackBar.open("Question sent successfully", 'Dismiss', {
        duration: 3000, panelClass: ['success-snackbar']
      });
      
    });
    this.form.reset();
  }

  ngOnInit(){
   
  }
}
