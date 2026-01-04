import { Component, Input, OnInit } from '@angular/core';
import { ThemeService } from '../../core/theme.service';
import { CommonModule } from '@angular/common';
import { LogoComponent } from './logo/logo.component';
import { UserDto } from '../dto/userDto';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-top-nav',
  standalone: true,
  imports: [CommonModule, LogoComponent,RouterLink],
  templateUrl: './top-nav.component.html',
  styleUrl: './top-nav.component.scss'
})
export class TopNavComponent implements OnInit {
  @Input()
  user!: UserDto
  userInitial = ''; 
  userRole = ""

  constructor(private theme: ThemeService, private router: Router) {}

  ngOnInit(): void {
    this.userInitial = this.user.userName.charAt(0)
    this.userRole = this.user.role[0]
  }

  get isDark() {
    return this.theme.isDark();
  }

  logout() {
    localStorage.removeItem('accessToken')
    this.router.navigate(['/auth', 'login'])
  }

  toggleTheme() {
    this.theme.toggleTheme();
  }
}
