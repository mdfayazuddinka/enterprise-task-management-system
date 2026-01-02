import { Routes } from '@angular/router';
import { TaskComponent } from './task/task.component';

export const TASK_ROUTES: Routes = [
  {
    path: '',
    component: TaskComponent,
    title: 'Task · Task Manager'
  }
];
