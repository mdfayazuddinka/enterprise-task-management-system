import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { TopNavComponent } from '../top-nav/top-nav.component';
import { UserDto } from '../dto/userDto';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [TopNavComponent, SidebarComponent, RouterOutlet],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.scss'
})
export class NavigationComponent implements OnInit {
  currentUser!: UserDto

  constructor(private authService: AuthService){}
  ngOnInit(): void {
    this.getCurrentUserInfo()
  }

  private getCurrentUserInfo() {
    this.currentUser = this.authService.getCurrentUserInfo()
  }
}
