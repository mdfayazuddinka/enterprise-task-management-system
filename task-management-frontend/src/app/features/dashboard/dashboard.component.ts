import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TaskSectionComponent } from './task-section/task-section.component';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { ProjectService } from '../../core/project.service';
import { ProjectDto } from '../enums/project';
import { filter } from 'rxjs';
import { TaskService } from '../task.service';
import { TaskDto, TaskEditDto, TaskStatus } from '../../layout/dto/Task';
import { AuthService } from '../../auth/auth.service';
import { Role } from '../../auth/roleEnum';
import { ToasterService } from '../../core/toaster.service';
import { EditTaskComponent } from '../modal/edit-task/edit-task.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, TaskSectionComponent, RouterLink, RouterOutlet, EditTaskComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  pendingTasks: TaskDto[] = [];
  inProgressTasks: TaskDto[] = [];
  qualityTasks: TaskDto[] = [];
  completedTasks: TaskDto[] = [];

  Tasks: TaskDto[] = [];

  selectedProject!: ProjectDto;
  canShowTasks!:boolean;
  isProjectAvailable!: boolean;
  showEditTaskModal!: boolean;
  showDeleteTaskModal!: boolean;
  selectedTask!: TaskDto;

  constructor(
    private projectService: ProjectService,
    private taskService: TaskService,
    private authService: AuthService,
    private toastService: ToasterService,
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
      this.selectedProject = project;
      this.isProjectAvailable = true;
      this.getTasksByProjectId(project);
    });
  };

  getTasksByProjectId(projectDto: ProjectDto) {
    this.taskService.getTasksByProjectId(projectDto.projectCode).subscribe({
      next: (tasksData) => {
        this.Tasks = tasksData;
        this.pendingTasks = tasksData.filter(task => task.status === TaskStatus.READY);
        this.inProgressTasks = tasksData.filter(task => task.status === TaskStatus.DEVELOPING);
        this.qualityTasks = tasksData.filter(task => task.status === TaskStatus.QA_TESTING);
        this.completedTasks = tasksData.filter(task => task.status === TaskStatus.DONE);
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
        this.editTask(event.task);
        break;
      case 'delete':
        this.showDeleteTaskModal = true;
        this.selectedTask = event.task;
        break;
    }
  }

  editTask(task: TaskDto) {
    this.showEditTaskModal = true;
    this.selectedTask = task;
  }

  deleteTask(task: TaskDto) {
    this.taskService.deleteTask(task.taskId).subscribe({
      next: () => {
        console.log('Task deleted successfully:', task);
        this.exitDeleteTaskModal();
        this.getTasksByProjectId(this.selectedProject);
      },
      error: (err) => {
        console.error('Error deleting task:', err);
      }
    });
  }

  handleSubmitForm(updatedTask: TaskEditDto) {
    this.taskService.updateTask(updatedTask).subscribe({
      next: (response) => {
        this.getTasksByProjectId(this.selectedProject);
      },
      error: (err) => {
        console.error("Error updating task:", err);
      }
    });
  }

  canAddTask() {
    this.authService.getCurrentUserInfo()
    const currentUser = this.authService.getCurrentUserInfo();
    const rolesList = [Role.ADMIN, Role.PROJECT_MANAGER, Role.QA_TESTER];
    if (currentUser && currentUser.role.some(role => rolesList.includes(role))) {
      this.router.navigate(['/app', { outlets: { popup: ['add-task'] } }]);
    } else {
      this.toastService.show("User is not authorized to add tasks.", 'error');
    }
  }

  handleCloseModal(canClose: boolean) {
    this.showEditTaskModal = canClose;
  }

  exitDeleteTaskModal() {
    this.showDeleteTaskModal = false;
  }

  confirmDelete() {
    this.deleteTask(this.selectedTask);
  }
}
