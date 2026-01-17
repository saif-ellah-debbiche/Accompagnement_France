import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { AuthRequest } from '../models/auth-request.module';
import { AuthResponse } from '../models/auth-response.module';
import { ChangePasswordRequest } from '../models/change-password-request.module';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private accessToken: string | null = null;
  private mustChangePassword = false;
  private refreshInProgress = false;
  private refreshSubject = new BehaviorSubject<string | null>(null);

  constructor(private http:HttpClient) { }

  setAccessToken(token: string, mustChangePassword: boolean = false) {
    this.accessToken = token;
    this.mustChangePassword = mustChangePassword;
    this.refreshSubject.next(token);
  }
  getMustChangePassword(){
   return this.mustChangePassword; 
  }

  getAccessToken(): string | null {
    return this.accessToken;
  }

  clearSession() {
    this.accessToken = null;
    this.refreshSubject.next(null);
    console.log("access token is "+this.accessToken)
  }


 login(authRequest: AuthRequest) {
  return this.http.post<AuthResponse>('/api/auth/login', authRequest, {
    withCredentials: true // receive refresh cookie
  }).pipe(
    tap(res => {
      console.log(res);
      this.setAccessToken(res.accessToken,res.mustChangePassword)})
  );
}
 // ===== REFRESH =====
  refresh(): Observable< AuthResponse> {
    console.log("start refreshing ")
    return this.http.post<AuthResponse >(
      '/api/auth/refresh',
      {},
      { withCredentials: true } // sends HTTP-only cookie
    ).pipe(
      tap(res => {
         console.log("refresh succeed")
         console.log("new token is "+res.accessToken)
        this.setAccessToken(res.accessToken,res.mustChangePassword)})
    );
  }
  startRefresh() {
    this.refreshInProgress = true;
    this.refreshSubject.next(null);
  }

  endRefresh() {
    this.refreshInProgress = false;
  }

  isRefreshing(): boolean {
    return this.refreshInProgress;
  }

  waitForRefresh(): Observable<string | null> {
    return this.refreshSubject.asObservable();
  }



  changePassword(changePasswordRequest:ChangePasswordRequest):Observable<AuthResponse>{
    return this.http.post<AuthResponse>("/api/auth/change-password",changePasswordRequest,{
      withCredentials:true
    }).pipe(
      tap(res => this.setAccessToken(res.accessToken,res.mustChangePassword))
    );
  }

  logout(){
    return this.http.post<void>("/api/auth/logout",{},{
      withCredentials:true
    }).pipe(
      tap(res => this.clearSession())
    );
  }

  resetPassword(token:string,password:string ,confirmPassword:string):Observable<string>{
      return this.http.post<string>("/api/auth/reset-password",{
        token,
        newPassword: password,
        confirmNewPassword : confirmPassword
      },{
      withCredentials:true
    })
  }
}
