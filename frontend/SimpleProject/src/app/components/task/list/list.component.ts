import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatCard, MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TaskService } from '@app/app/services/task.service';
import { TaskComponent } from "../task.component";
import { AddTaskComponent } from '../add-task/add-task.component';
import { DeleteTaskComponent } from "../delete-task/delete-task.component";

@Component({
  selector: 'app-list',
  imports: [MatProgressSpinnerModule, MatCard, MatCardModule, MatButton, AddTaskComponent, DeleteTaskComponent],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent {
  taskService = inject(TaskService);

  ngOnInit(): void {
    this.taskService.getTasks();
  }

}
