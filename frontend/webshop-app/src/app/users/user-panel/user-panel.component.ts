import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/auth/services/auth-service.service';
import { ChangePasswordRequest } from 'src/app/models/update-password-request';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PhotoService } from 'src/app/products/services/photo.service';

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css']
})
export class UserPanelComponent {

  form: FormGroup;

  form2: FormGroup;

  passwordChangeRequest?: ChangePasswordRequest = {
    oldPassword: '',
    newPassword: '',
  }

  user: User | null | undefined;

  isEditing: boolean = false;
  isPasswordEditing: boolean = false;
  constructor(
    private userService: UserService,
    formBuilder: FormBuilder,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
    private photoService: PhotoService) {
    this.user = this.authService.currentUser;

    this.form = formBuilder.group({
      username: this.user?.username,
      email: this.user?.email,
      name: this.user?.name,
      lastName: this.user?.lastName,
      city: this.user?.city,
      password: this.user?.password

    });

    this.form2 = formBuilder.group({
      oldPassword: this.passwordChangeRequest?.oldPassword,
      newPassword: this.passwordChangeRequest?.newPassword,
    });
    if (this.user?.avatar != null) {
      this.tempImage = this.user?.avatar!;
    };


  }

  tempImage: string = "";

  ngOnInit() {

  }

  savePassword() {

    this.userService.updatePassword(this.form2.value, this.user?.id!).subscribe((data: any) => {
      this.authService.setToken(data.token);
      this.isPasswordEditing = false;
      this._snackBar.open("Password successfully changed", 'Dismiss', {
        duration: 3000,
      });
      this.cancelPasswordEdit();
    },
      (error) => {
        this._snackBar.open("Wrong password", 'Dismiss', {
          duration: 3000,
        });
        var req = new ChangePasswordRequest();

        this.form2.patchValue({
          oldPassword: this.passwordChangeRequest?.oldPassword,
          newPassword: this.passwordChangeRequest?.newPassword,
        });
      });
  }

  showPasswordFields() {
    this.isPasswordEditing = true;
  }

  isFormValid(): boolean {
    var u: User = this.form.value;
    if (this.authService.currentUser?.city == u.city
      && this.authService.currentUser?.email == u.email
      && this.authService.currentUser?.lastName == u.lastName
      && this.authService.currentUser?.name == u.name
      && this.tempImage.length == 0) {

      return false;

    }

    return this.form.valid;
  }

  isPasswordFormValid() {
    var req: ChangePasswordRequest = this.form2.value;
    if (req.newPassword?.length == 0
      || req.oldPassword?.length == 0) {

      return false;

    }

    return this.form2.valid;
  }

  onFileSelected(event: any) {

    this.photoService.processImage(event.target.files[0]).then((resultString) => {

      this.tempImage = resultString
    })
      .catch((error) => {
         
        console.error(error);
      });

  }

  save() {
    var u: User = this.form.value;
    if (this.tempImage != this.user?.avatar) u.avatar = this.tempImage;
    else u.avatar = "";

    if (this.form.get("password")?.value == null || this.form.get("password")?.value.length == 0) {
      this._snackBar.open("Please provide your password", 'Dismiss', {
        duration: 3000,
      });
      return;
    }

    this.userService.updateUser(u, this.user?.id!).subscribe((data: any) => {
      this.authService.setToken(data.token);
      this.authService.currentUser = null;
      this.authService.refreshUserInfo();
      this.isEditing = false;

      this._snackBar.open("Changes saved", 'Dismiss', {
        duration: 3000,
      });
    },
      (error) => {
        this._snackBar.open("Wrong password", 'Dismiss', {
          duration: 3000,
        });

      });
    this.form.get("password")?.setValue("");

  }

  edit() {
    this.isEditing = true;
  }

  cancel() {
    this.isEditing = false;
    this.user = this.authService.currentUser;
    this.form.patchValue({
      username: this.user?.username,
      email: this.user?.email,
      name: this.user?.name,
      lastName: this.user?.lastName,
      city: this.user?.city,
      password: this.user?.password,


    });
    this.tempImage = this.user?.avatar!;

  }

  cancelPasswordEdit() {
    this.isPasswordEditing = false;
    var req = new ChangePasswordRequest();

    this.form2.patchValue({
      oldPassword: this.passwordChangeRequest?.oldPassword,
      newPassword: this.passwordChangeRequest?.newPassword,
    });
  }
}
