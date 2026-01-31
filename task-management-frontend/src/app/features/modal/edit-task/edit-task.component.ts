import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TaskDto, TaskPriority, TaskStatus, TaskType } from '../../../layout/dto/Task';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-edit-task',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './edit-task.component.html',
  styleUrl: './edit-task.component.scss'
})
export class EditTaskComponent implements OnInit {
  @Input() task!: TaskDto;
  @Output() canCloseModal = new EventEmitter<boolean>();
  @Output() TaskEditDto = new EventEmitter<TaskDto>();

  taskStatuses = Object.values(TaskStatus);
  taskPriorities = Object.values(TaskPriority);
  taskTypes = Object.values(TaskType);
  usersList!: String[];
  taskForm!: FormGroup;
  taskType= ""

  constructor(private router: Router, private route: ActivatedRoute, private formbuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.taskType = this.task.type;
    this.taskForm = this.formbuilder.group({
          title: [this.task.title, Validators.required],
          type: [this.taskType, Validators.required],
          priority: [this.task.priority, Validators.required],
          status: [this.task.status, Validators.required],
          assignedTo: [this.task.assignedTo, Validators.required],
          description: [this.task.description],
          taskId: [this.task.taskId]
        });
    this.authService.getAllUserNames().subscribe({
      next: users => this.usersList = users
    });
  }

  submitForm() {
    if (this.taskForm.valid) {
      this.TaskEditDto.emit(this.taskForm.value);
      this.closeModal();
    }
  }

  closeModal() {
    this.router.navigate([{ outlets: { popup: null } }], { relativeTo: this.route.parent });
    this.taskForm.reset();
    this.canCloseModal.emit(false)
  }

}
