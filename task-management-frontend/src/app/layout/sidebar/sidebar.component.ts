import { Component } from '@angular/core';
import { ThemeService } from '../../core/theme.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  constructor(private theme: ThemeService) { }

  collapsed = false;
  projectsOpen = false;

  projects = [
    'Alpha',
    'Beta',
    'Gamma',
    'Delta',
    'Omega',
    'Sigma',
    'Orion',
    'Nova',
    'More X'
  ];

  visibleLimit = 7;
  toggleSidebar() {
    this.collapsed = !this.collapsed;
  }

  toggleProjects() {
    this.projectsOpen = !this.projectsOpen;
  }

  openProjectsModal() {
    alert("Please check for more projects")
  }

  addProject() {
  alert('Add project clicked');
}

  get visibleProjects() {
    return this.projects.slice(0, this.visibleLimit);
  }

  get hasMoreProjects() {
    return this.projects.length > this.visibleLimit;
  }
  get isDark() {
    return this.theme.isDark();
  }
}
