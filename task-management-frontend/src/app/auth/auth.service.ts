import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_ENDPOINTS } from '../core/api-endpoints';
import { LoginRequestDto } from './dto/loginRequestDto';
import { Observable } from 'rxjs';
import { SignupRequestDto } from './dto/signupRequestDto';
import { SignUpResponseDto } from './dto/signUpResponseDto';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

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
}
