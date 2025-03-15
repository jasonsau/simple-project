import { Component } from '@angular/core';
import { ListComponent } from "@components/task/list/list.component";
import { AddTaskComponent } from '@components/task/add-task/add-task.component';

@Component({
  selector: 'app-task',
  imports: [ListComponent, AddTaskComponent ],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css'
})
export class TaskComponent {

}
