import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('accessToken');

  // ❌ No token → redirect to login
  if (!token) {
    return router.createUrlTree(
      ['/auth/login'],
      { queryParams: { returnUrl: state.url } }
    );
  }

  // ❌ Expired token → cleanup & redirect
  if (isTokenExpired(token)) {
    localStorage.removeItem('accessToken');
    return router.createUrlTree(
      ['/auth/login'],
      { queryParams: { returnUrl: state.url } }
    );
  }

  // ✅ Token valid → allow navigation
  return true;
};

function isTokenExpired(token: string): boolean {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const exp = payload.exp * 1000;
    return Date.now() > exp;
  } catch {
    return true;
  }
}
