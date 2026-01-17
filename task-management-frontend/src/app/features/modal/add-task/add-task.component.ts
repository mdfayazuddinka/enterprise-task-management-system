import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../../../core/project.service';
import { ProjectDto, ProjectPriority, ProjectStatus } from '../../enums/project';
import { ToasterService } from '../../../core/toaster.service';
import { CommonModule } from '@angular/common';
import { UserDto } from '../../../layout/dto/userDto';
import { AuthService } from '../../../auth/auth.service';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from '../../task.service';
import { TaskCreationDto, TaskPriority, TaskType } from '../../../layout/dto/Task';
import { filter } from 'rxjs';
import { TaskStatus } from '../../../layout/dto/Task';

@Component({
  selector: 'app-add-task',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './add-task.component.html',
  styleUrl: './add-task.component.scss'
})
export class AddTaskComponent implements OnInit {
  userInfo!: UserDto;
  projectsList!: ProjectDto[];
  taskStatuses = Object.values(TaskStatus);
  taskPriorities = Object.values(TaskPriority);
  taskTypes = Object.values(TaskType);
  usersList!: String[];
  taskCreationDto!: TaskCreationDto;
  project = '';
  taskForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private toaster: ToasterService,
    private authService: AuthService,
    private taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.projectService.project$
    .pipe(filter((project): project is ProjectDto => project !== null))
    .subscribe(project => {
      this.project = project.name;
    });
    this.userInfo = this.authService.getCurrentUserInfo();

    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      type: ['', Validators.required],
      priority: [TaskPriority.LOW, Validators.required],
      status: [TaskStatus.TO_DO, Validators.required],
      assignedTo: ['', Validators.required],
      createdBy: [this.userInfo.userName],
      createdDate: [new Date().toISOString().split('T')[0], Validators.required],
      dueDate: ['', Validators.required],
      description: [''],
      comment: ['']
    });

    this.projectService.getAllProjects().subscribe({
      next: projects => this.projectsList = projects,
      error: err => this.toaster.show(err, 'error')
    });
    this.getUserNames();
  }

  getUserNames() {
    this.authService.getAllUserNames().subscribe({
      next: users => this.usersList = users
    });
  }

  submitForm() {
    if (this.taskForm.valid) {
      const projectName = this.taskForm.get('project')?.value; 
      const projectCode = this.projectsList.find(p => p.name === projectName)?.projectCode;
      this.taskCreationDto = 
      { ...this.taskForm.value,
        projectId: projectCode,
        createdBy: this.userInfo.userName, 
        createdDate: new Date().toISOString()} as TaskCreationDto;
        
      this.taskService.createTask(this.taskCreationDto).subscribe({
        next: (task) => {
          this.closeModal()
        },
        error: (error) => {
          this.toaster.show(`Unable to create task ${error.message}`, 'error')
        }
      })
    } else {
      this.toaster.show('Form is invalid', 'error');
      this.taskForm.markAllAsTouched();
    }
  }

  closeModal() {
    this.router.navigate([{ outlets: { popup: null } }], { relativeTo: this.route.parent });
  }
}
