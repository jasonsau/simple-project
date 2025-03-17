import { Component, inject, input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogActions, MatDialogContent, MatDialogRef } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { TaskService } from '@services/task.service';
import { Task } from '@models';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-delete-task',
  imports: [MatButtonModule, MatDividerModule, MatIconModule],
  templateUrl: './delete-task.component.html',
  styleUrl: './delete-task.component.css'
})
export class DeleteTaskComponent {
  task = input<Task>();
  readonly dialog = inject(MatDialog);

  openDialog() {
    this.dialog.open(DeleteTaskComponentDialog, {data: {task: this.task()}});
  }

}

@Component({
  selector: 'app-delete-task-dialog',
  templateUrl: './delete-task-dialog.component.html',
  imports: [MatButtonModule, MatDividerModule, MatIconModule, MatDialogContent, MatDialogActions],
})
export class DeleteTaskComponentDialog {
  private _snackBar = inject(MatSnackBar);
  readonly dialogRef = inject(MatDialogRef<DeleteTaskComponentDialog>);
  taskInput = inject(MAT_DIALOG_DATA);
  taskService = inject(TaskService);

  closeDialog() {
    this.dialogRef.close();
  }

  deleteTask() {
    this.taskService.deleteTask(this.taskInput.task).subscribe(() => {
      this.openSnackBar('Tarea eliminada', 'Close');
      this.dialogRef.close();
      this.taskService.getTasks();
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  }
}
