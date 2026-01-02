import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';

export const AUTH_ROUTES: Routes = [
  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent, title: 'Login · Task Manager' },
      { path: 'signup', component: SignupComponent, title: 'Sign Up · Task Manager' },
      { path: '', redirectTo: 'login', pathMatch: 'full' }
    ]
  }
];
