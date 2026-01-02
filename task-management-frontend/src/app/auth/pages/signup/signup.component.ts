import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../auth.service';
import { Role } from '../../roleEnum';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  loading = false;
  submitted = false;

  passwordVisible = false;
  confirmPasswordVisible = false;

  roles = Object.keys(Role);

  passwordStrengthPercent = 0;
  passwordStrengthLabel = '';

  passwordCriteria = [
    { key: 'length', label: 'At least 8 characters', isValid: false },
    { key: 'upperLower', label: 'Upper & lower case letters', isValid: false },
    { key: 'number', label: 'Contains number', isValid: false },
    { key: 'special', label: 'Contains a special character', isValid: false },
    { key: 'noSpaces', label: 'No spaces', isValid: false },
    { key: 'noRepeat', label: 'No repeated characters (aaa)', isValid: false },
    { key: 'noSequence', label: 'No sequential characters (abc / 123)', isValid: false }
  ];

  signupForm = this.fb.nonNullable.group({
    userName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required],
    role: [null as string | null, Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.signupForm.controls.password.valueChanges.subscribe(value => {
      this.checkPasswordStrength(value ?? '');
    });
  }

  // ---------------- PASSWORD STRENGTH ----------------
  checkPasswordStrength(password: string): void {
    if (!password) {
      this.resetPasswordStrength();
      return;
    }

    const lower = password.toLowerCase();

    const checks = {
      length: password.length >= 8,
      upperLower: /(?=.*[a-z])(?=.*[A-Z])/.test(password),
      number: /\d/.test(password),
      special: /[!@#$%^&*]/.test(password),
      noSpaces: !password.includes(' '),
      noRepeat: !/(.)\1{2,}/.test(password),
      noSequence: !this.hasSequence(lower)
    };

    Object.entries(checks).forEach(([key, value]) =>
      this.setCriteria(key, value)
    );

    const passed = this.passwordCriteria.filter(c => c.isValid).length;
    this.passwordStrengthPercent = Math.round(
      (passed / this.passwordCriteria.length) * 100
    );

    this.passwordStrengthLabel =
      this.passwordStrengthPercent === 100 ? 'Strong'
        : this.passwordStrengthPercent >= 60 ? 'Medium'
          : 'Weak';
  }

  private resetPasswordStrength(): void {
    this.passwordCriteria.forEach(c => c.isValid = false);
    this.passwordStrengthPercent = 0;
    this.passwordStrengthLabel = '';
  }

  private hasSequence(value: string): boolean {
    for (let i = 0; i < value.length - 2; i++) {
      const a = value.charCodeAt(i);
      const b = value.charCodeAt(i + 1);
      const c = value.charCodeAt(i + 2);
      if ((b === a + 1 && c === b + 1) || (b === a - 1 && c === b - 1)) {
        return true;
      }
    }
    return false;
  }

  private setCriteria(key: string, value: boolean): void {
    const criteria = this.passwordCriteria.find(c => c.key === key);
    if (criteria) criteria.isValid = value;
  }

  // ---------------- SUBMIT ----------------
  submit(): void {
    this.submitted = true;

    if (
      this.signupForm.controls.password.value !==
      this.signupForm.controls.confirmPassword.value
    ) {
      this.signupForm.controls.confirmPassword.setErrors({ notMatched: true });
    }

    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    const payload = {
      userName: this.signupForm.controls.userName.value,
      email: this.signupForm.controls.email.value,
      password: this.signupForm.controls.password.value,
      role: [Role[this.signupForm.controls.role.value as keyof typeof Role]]
    };

    this.authService.signup(payload).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/auth/login']);
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  togglePasswordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }

  toggleConfirmPasswordVisibility(): void {
    this.confirmPasswordVisible = !this.confirmPasswordVisible;
  }

  hasError(field: string, error: string): boolean {
    const control = this.signupForm.get(field);
    return !!(control && control.errors && control.errors[error]);
  }

  isPasswordCriteriaValid(key: string): boolean {
    const crit = this.passwordCriteria.find(c => c.key === key);
    return !!crit?.isValid;
  }

}

