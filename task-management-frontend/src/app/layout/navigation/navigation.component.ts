import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { TopNavComponent } from '../top-nav/top-nav.component';
import { UserDto } from '../dto/userDto';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [TopNavComponent, SidebarComponent, RouterOutlet],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.scss'
})
export class NavigationComponent implements OnInit {
  currentUser!: UserDto
  ngOnInit(): void {
    this.getCurrentUserInfo()
  }

  private getCurrentUserInfo() {
    const token = localStorage.getItem('accessToken');
    const payload = JSON.parse(atob(token!.split('.')[1]));
    this.currentUser = payload
  }
}
