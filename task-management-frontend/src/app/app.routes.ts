import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () =>
      import('./auth/auth.routes').then(m => m.AUTH_ROUTES)
  },
  {
    path: '',
    loadChildren: () =>
      import('./layout/navigation.route')
        .then(m => m.NAVIGATION_ROUTES)
  },
  {
    path: '**',
    redirectTo: 'auth/login'
  }
];
