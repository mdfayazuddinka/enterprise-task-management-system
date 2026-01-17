import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_ENDPOINTS } from '../core/api/api-endpoints';
import { LoginRequestDto } from './dto/loginRequestDto';
import { SignupRequestDto } from './dto/signupRequestDto';
import { SignUpResponseDto } from './dto/signUpResponseDto';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) { }

  login(loginForm: LoginRequestDto) {
    return this.http.post<boolean>(
      API_ENDPOINTS.AUTH.LOGIN,
      loginForm,
      { observe: 'response' }
    );
  }

  signup(payload: SignupRequestDto) {
    return this.http.post<SignUpResponseDto>(
      API_ENDPOINTS.AUTH.SIGNUP,
      payload
    );
  }

  getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  // refreshToken(): Observable<string> { 
  //   return this.http.post<{ token: string }>(
  //     API_ENDPOINTS.AUTH.REFRESH, {})
  //     .pipe(tap(response => { 
  //       localStorage.setItem('jwtToken', response.token); }), 
  //       map(response => response.token)); }

  getCurrentUserInfo() {
    const token = localStorage.getItem('accessToken');
    const payload = JSON.parse(atob(token!.split('.')[1]));
    return payload
  }

  getAllUserNames(): Observable<String[]> {
    return this.http.get<String[]>(
      API_ENDPOINTS.AUTH.GET_USER_NAMES
    )
  }
}
