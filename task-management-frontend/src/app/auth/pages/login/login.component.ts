import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { Router, RouterLink } from '@angular/router';
import { LoginRequestDto } from '../../dto/loginRequestDto';
import { CommonModule } from '@angular/common';
import { ToasterService } from '../../../core/toaster.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loading = false;
  submitted = false;
  errorMessage = '';
  passwordVisible = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private toaster: ToasterService,
  ) {}

  // ✅ Reactive Form
  loginForm = this.fb.group({
    identifier: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });

  /** Easy access */
  get f() {
    return this.loginForm.controls;
  }

  submit(): void {
    this.submitted = true;

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const request = this.buildLoginRequest();

    this.authService.login(request).subscribe({
      next: (result) => {
        const token = result.headers.get('Authorization');
        if (token) {
          localStorage.setItem('accessToken', token);
        }
        this.toaster.show("Login Successful", 'success')
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.errorMessage =
          err.error?.message || 'Invalid username/email or password';
        this.loading = false;
        this.toaster.show(this.errorMessage, 'error')
      }
    });
  }

  private buildLoginRequest(): LoginRequestDto {
    const { identifier, password } = this.loginForm.value;

    return {
      identifier: identifier!,
      password: password!
    };
  }

  togglePasswordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }
}
