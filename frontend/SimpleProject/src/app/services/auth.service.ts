import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import {environment} from "@app/environments/environment"
import { AuthUserRequest, AuthUserResponse } from '../models';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  httpClient = inject(HttpClient);

  constructor() { }

  login(userRequest: AuthUserRequest): Observable<AuthUserResponse> {
    return this.httpClient.post<AuthUserResponse>(`${environment.urlApi}/auth/login`, userRequest)
  }

  saveToken(token: string, refreshToken: string) {
    localStorage.setItem('token', token);
    localStorage.setItem('refreshToken', refreshToken);
  }

  logout() {
    localStorage.clear();
  }
}
