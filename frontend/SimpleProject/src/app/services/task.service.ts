import { isPlatformServer } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { computed, inject, Injectable, PLATFORM_ID, signal } from '@angular/core';
import {environment} from "@app/environments/environment"
import { PageContent, Task } from '@models';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  platformId = inject(PLATFORM_ID);
  httpClient = inject(HttpClient);
  #tasks = signal({
    paginator: {},
    content: [] as Task[]
  });
  #loading = signal<boolean>(false);
  tasks = computed(() => this.#tasks().content);
  loading = computed(() => this.#loading());


  getTasks() {
    this.#loading.set(true);
    return this.httpClient.get<PageContent<Task>>(`${environment.urlApi}/tasks`)
    .subscribe((result) => {
      this.#tasks.set({
        paginator: result.page,
        content: result.content
      });
      this.#loading.set(false);
    });
  }

  saveTask(task: Task): Observable<Task> {
    return this.httpClient.post<Task>(`${environment.urlApi}/tasks`, task)
  }

  updateTask(task: Task): Observable<Task> {
    return this.httpClient.put<Task>(`${environment.urlApi}/tasks/${task.id}`, task)
  }
}
