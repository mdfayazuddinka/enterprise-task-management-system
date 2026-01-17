import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TaskSectionComponent } from './task-section/task-section.component';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { ProjectService } from '../../core/project.service';
import { ProjectDto } from '../enums/project';
import { filter } from 'rxjs';
import { TaskService } from '../task.service';
import { TaskDto } from '../../layout/dto/Task';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, TaskSectionComponent, RouterLink, RouterOutlet],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  pendingTasks: TaskDto[] = [];
  inProgressTasks: TaskDto[] = [];
  completedTasks: TaskDto[] = [];

  Tasks: TaskDto[] = [];

  selectedProject!: ProjectDto;
  canShowTasks!:boolean;
  isProjectAvailable!: boolean;

  constructor(
    private projectService: ProjectService,
    private taskService: TaskService,
    private router: Router) {}

  ngOnInit(): void {
    this.setSelectedProject();
    this.router.events 
    .pipe(filter(event => event instanceof NavigationEnd)) 
    .subscribe(() => { 
      if (this.selectedProject) { 
        this.canShowTasks = false;
        this.getTasksByProjectId(this.selectedProject); 
      } 
    });
  };

  setSelectedProject() {
    this.projectService.project$
    .pipe(filter((project): project is ProjectDto => project !== null)).subscribe(project => {
      console.log("selected project:", project);
      this.selectedProject = project;
      this.isProjectAvailable = true;
      this.getTasksByProjectId(project);
    });
  };

  getTasksByProjectId(projectDto: ProjectDto) {
    this.taskService.getTasksByProjectId(projectDto.projectCode).subscribe({
      next: (tasksData) => {
        this.Tasks = tasksData;
        this.pendingTasks = tasksData.filter(t => t.status === 'TO_DO');
        this.inProgressTasks = tasksData.filter(t => t.status === 'IN_PROGRESS');
        this.completedTasks = tasksData.filter(t => t.status === 'COMPLETED');
        this.canShowTasks = this.Tasks.length > 0;
      },
      error: (err) => {
        console.error("Error fetching tasks:", err);
      }
    });
  }

  handleTaskAction(event: { action: string; task: TaskDto }) {
    switch (event.action) {
      case 'move':
        console.log('Moving task to next stage from dashboard:', event.task);
        break;
      case 'edit':
        console.log('Editing task from dashboard:', event.task);
        break;
      case 'delete':
        this.deleteTask(event.task);
        break;
    }
  }

  deleteTask(task: TaskDto) {
    this.taskService.deleteTask(task.id).subscribe({
      next: () => {
        console.log('Task deleted successfully:', task);
        this.getTasksByProjectId(this.selectedProject);
      },
      error: (err) => {
        console.error('Error deleting task:', err);
      }
    });
  }
} 
