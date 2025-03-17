import { isPlatformServer } from '@angular/common';
import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const platform = inject(PLATFORM_ID);
  if(isPlatformServer(platform)) {
    return next(req);
  }
  const token = localStorage.getItem('token');
  const refreshToken = localStorage.getItem('refreshToken');
  let newHeaders = req.headers;
  if(token) { 
    newHeaders = newHeaders.set('Authorization', `Bearer ${token}`);
  }
  const newRequest = req.clone({ headers: newHeaders });
  return next(newRequest)
    .pipe(catchError((error: HttpErrorResponse) => {
      if(error.status === 401) {
        const newHeaders = req.headers.set('Authorization', `Bearer ${refreshToken}`);
        const newRequest = req.clone({ headers: newHeaders });
        return next(newRequest);
      }
      return throwError(() => error);
    })
  );
};
