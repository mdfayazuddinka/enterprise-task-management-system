import { Routes } from '@angular/router';
import { NavigationComponent } from './navigation/navigation.component';
import { authGuard } from '../core/guards/auth.guard';

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
            .then(m => m.DashboardComponent)
      },
      {
        path: 'analytics',
        loadComponent: () =>
          import('../features/analytics/analytics.component')
            .then(m => m.AnalyticsComponent)
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
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  }
];
