import { Component, inject } from '@angular/core';
import {MatList, MatListItem, MatListItemLine, MatListItemTitle} from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TaskService } from '@app/app/services/task.service';

@Component({
  selector: 'app-list',
  imports: [MatList, MatListItem, MatListItemLine, MatListItemTitle, MatProgressSpinnerModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent {
  taskService = inject(TaskService);

  ngOnInit(): void {
    this.taskService.getTasks();
  }

}
