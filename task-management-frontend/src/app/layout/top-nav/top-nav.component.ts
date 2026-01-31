import { Component, ElementRef, HostListener, Input, OnInit } from '@angular/core';
import { ThemeService } from '../../core/theme.service';
import { CommonModule } from '@angular/common';
import { LogoComponent } from './logo/logo.component';
import { UserDto } from '../dto/userDto';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-top-nav',
  standalone: true,
  imports: [CommonModule, LogoComponent, RouterLink],
  templateUrl: './top-nav.component.html',
  styleUrl: './top-nav.component.scss'
})
export class TopNavComponent implements OnInit {
  @Input()
  user!: UserDto

  @HostListener('document:click', ['$event']) 
  onClickOutside(event: Event) { 
    if (!this.eRef.nativeElement.contains(event.target)) { 
      this.dropdownOpen = false; 
    } 
  }
  
  userInitial = '';
  userRole = ""

  dropdownOpen = false;

  constructor(private theme: ThemeService, private router: Router, private eRef: ElementRef) { }

  ngOnInit(): void {
    this.userInitial = this.user.userName.charAt(0)
    this.userRole = this.user.role[0]
  }

  get isDark() {
    return this.theme.isDark();
  }

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  logout() {
    localStorage.removeItem('accessToken')
    this.router.navigate(['/auth', 'login'])
  }

  toggleTheme() {
    this.theme.toggleTheme();
  }
}
