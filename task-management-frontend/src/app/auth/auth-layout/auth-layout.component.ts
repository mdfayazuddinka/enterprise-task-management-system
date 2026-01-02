import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ThemeService } from '../../core/theme.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-auth-layout',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './auth-layout.component.html',
  styleUrl: './auth-layout.component.scss'
})
export class AuthLayoutComponent {

  constructor(private theme: ThemeService) {}

  get isDark() {
    return this.theme.isDark();
  }

  toggleTheme() {
    this.theme.toggleTheme();
  }

}
