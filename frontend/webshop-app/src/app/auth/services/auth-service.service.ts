import { HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaderResponse, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, catchError, defer, map, of, tap, throwError } from 'rxjs';
import { MessageService } from 'src/app/messages/services/message.service';
import { ActivationRequest } from 'src/app/models/activation-request';
import { ExpiredJwtResponse } from 'src/app/models/expired-jwt-response';
import { LoginRequest } from 'src/app/models/login-request';
import { SignUpRequest } from 'src/app/models/sign-up-request';
import { User } from 'src/app/models/user';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService implements HttpInterceptor {
  setToken(token: any) {
    localStorage.setItem("token", token);
  }
  refreshUserInfo() {
    if (this.currentUser == null) {
      if (localStorage.getItem("token") != null && localStorage.getItem("token") != undefined)
        this.getLoggedInUserData(localStorage.getItem("token")!).subscribe(data => this.currentUser = data)
    }
  }

  public loggedIn: boolean = false;
  private _isActivationPending: boolean = false;

  

  private baseUrl = environment.apiUrl + "/api/v1/auth";

  public currentUser?: User | null = null;





  getLoggedInUserData(token: string): Observable<User> {
    if (this.currentUser == null || this.currentUser == undefined) {
      
      return this.extractTokenInfo(token).pipe(
        tap((data: User) => {
          this.currentUser = data;
         


        },
        )
      );
    }
    return of(this.currentUser);
  };




  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {
    
  }

  requestOptions = {
    withCredentials: true
  };



  set isActivationPending(value: boolean) {
    this._isActivationPending = value;
  }


  get isActivationPending(): boolean {
    return this._isActivationPending;
  }

  isTokenValid() {

    return this.http.post<any>(`${this.baseUrl}/checkToken`, this.requestOptions);

  }



  activateUser(activationRequest: ActivationRequest) {
    return this.http.post<any>(`${this.baseUrl}/activate`, activationRequest, this.requestOptions);
  }

  loginUser(loginRequest: LoginRequest) {
    return this.http.post<any>(`${this.baseUrl}/login`, loginRequest, this.requestOptions);
  }

  signup(signupRequest: SignUpRequest) {
    return this.http.post<User>(`${this.baseUrl}/sign-up`, signupRequest, this.requestOptions);
  }

  extractTokenInfo(token: string | undefined) {
    return this.http.post<User>(`${this.baseUrl}/info`, token, this.requestOptions);;
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = localStorage.getItem('token');
   
    
    if (token != null) {

      request = request.clone({
        setHeaders: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      this.getLoggedInUserData(token);


    }
    else {
      this.loggedIn = false;
    }

    return next.handle(request).pipe(

      tap((event) => {
        if (event instanceof HttpResponse) {

        }
      }),

      catchError((error: HttpErrorResponse) => {
        const errorMessage = error.headers.get('Errormessage');

        if (errorMessage == "Token expired.") {
           ;
          const newJwt: ExpiredJwtResponse = error.error as ExpiredJwtResponse;
          localStorage.setItem('token', newJwt.token);

          const newRequest = request.clone({
            setHeaders: {
              Authorization: `Bearer ${newJwt.token}`
            }
          });
           
          // Retry the request with the new token
          return next.handle(newRequest);

        } else if (errorMessage == "For security reasons, please log in again.") {
          localStorage.removeItem('token');
          this.loggedIn = false;
        }
        else{
          this.messageService.setMessage("Something went wrong. Please try again later.")
        }

        return throwError(() => error)
      })
    );

  }

  getObjectHash(obj: any): number {
    if (obj === null || obj === undefined) {
      return 0; // Default hash code for null or undefined objects
    }

    // Convert the object to a JSON string and hash it
    const jsonString = JSON.stringify(obj);
    let hash = 0;
    if (jsonString.length === 0) {
      return hash;
    }

    for (let i = 0; i < jsonString.length; i++) {
      const char = jsonString.charCodeAt(i);
      hash = (hash << 5) - hash + char;
    }

    return hash;
  }


}
