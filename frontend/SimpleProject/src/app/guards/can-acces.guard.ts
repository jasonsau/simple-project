import { isPlatformBrowser } from '@angular/common';
import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const canAccesGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const platform = inject(PLATFORM_ID);
  if(isPlatformBrowser(platform)) {
    const token = localStorage.getItem('token');
    if(!token) {
      router.navigate(['/']);
      return false;
    }
  }
  return true;
};
