import { Component, OnInit } from '@angular/core';
import { ThemeService } from '../../core/theme.service';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ProjectService } from '../../core/project.service';
import { ProjectDto } from '../../features/enums/project';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  collapsed = false;
  projectsOpen = false;
  projects!:ProjectDto[];
  visibleLimit = 7;

  constructor(
    private theme: ThemeService, 
    private projectService: ProjectService,
    private router: Router) {}

  ngOnInit(): void {
    this.getAllProjects();
  }

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
    this.router.navigate(['/app', { outlets: { popup: ['add-project'] } }])
  }

  getSelectedProject(selectedProject: ProjectDto) {
    this.projectService.setSelectedProject(selectedProject)
  }

  getAllProjects() {
    this.projectService.getAllProjects().subscribe({
      next: (response) => {
        this.projects = response
        if (this.projects && this.projects.length > 0) { 
          this.projectService.setSelectedProject(this.projects[0]); 
        } else { 
          console.log("No projects available"); 
        }
      },
      error: (error) => {
        console.log("error: ", error)
      }
    })
  }

  get hasMoreProjects() {
    return this.projects.length > this.visibleLimit;
  }
  get isDark() {
    return this.theme.isDark();
  }
}
