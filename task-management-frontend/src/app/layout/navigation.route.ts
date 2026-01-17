import { Routes } from '@angular/router';
import { NavigationComponent } from './navigation/navigation.component';
import { authGuard } from '../core/guards/auth.guard';
import { AddProjectComponent } from '../features/modal/add-project/add-project.component';
import { AddTaskComponent } from '../features/modal/add-task/add-task.component';

export const NAVIGATION_ROUTES: Routes = [
  {
    path: '',
    component: NavigationComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('../features/dashboard/dashboard.component')
            .then(m => m.DashboardComponent),
        title: 'Dashboard · Task Manager'
      },
      {
        path: 'analytics',
        loadComponent: () =>
          import('../features/analytics/analytics.component')
            .then(m => m.AnalyticsComponent),
      },
      {
        path: 'teams',
        loadComponent: () =>
          import('../features/teams/teams.component')
            .then(m => m.TeamsComponent)
      },
      {
        path: 'project',
        loadComponent: () =>
          import('../features/projects/projects.component')
            .then(m => m.ProjectsComponent)
      },
      {
        path: 'add-project',
        component: AddProjectComponent,
        outlet: 'popup'
      },
      {
        path: 'add-task',
        component: AddTaskComponent,
        outlet: 'popup'
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  }
];
