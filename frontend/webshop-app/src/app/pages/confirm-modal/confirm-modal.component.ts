import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.css']
})
export class ConfirmModalComponent {
  constructor(private dialogRef: MatDialogRef<ConfirmModalComponent>) { }

  ngOnInit() {
  }

  close() {
    this.dialogRef.close(false);
  }


  confirm() {
    this.dialogRef.close(true);
  }
}
