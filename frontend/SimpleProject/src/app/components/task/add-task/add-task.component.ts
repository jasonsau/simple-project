import { Component, inject, Inject, input, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon'
import {MatButtonModule} from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogModule,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { TaskService } from '@app/app/services/task.service';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { MessageError, Task } from '@models';
import { catchError, delay, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';



@Component({
  selector: 'app-add-task',
  imports: [MatButtonModule, MatDividerModule, MatIconModule],
  templateUrl: './add-task.component.html',
  styleUrl: './add-task.component.css',
})
export class AddTaskComponent {
  readonly dialog = inject(MatDialog);

  openDialog() {
    const dialogRef = this.dialog.open(AddTaskComponentDialog)
  }
}

@Component({
  selector: 'app-add-task-dialog',
  templateUrl: './add-task-dialog.component.html',
  imports: [
    MatDialogModule, 
    MatDialogContent, 
    MatDialogTitle, 
    MatFormFieldModule, 
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    FormsModule,
  ],

})
export class AddTaskComponentDialog {
  readonly dialogRef = inject(MatDialogRef<AddTaskComponentDialog>);
  taskService = inject(TaskService);
  loading = signal(false);
  taskFormGroup = new FormGroup({
    title: new FormControl('', {
      validators: [Validators.required],
    }),
    description: new FormControl('',{
      validators: [Validators.required]
    }),
  });
  taskForm = {
    title: new FormControl('', {
      validators: [Validators.required],
    }),
    description: new FormControl('',{
      validators: [Validators.required]
    }),
  }
  testError = "test";
  private _snackBar = inject(MatSnackBar);

  onNoClick(): void {
    this.dialogRef.close();
  }

  handleAddTask() {
    this.loading.set(true);
    const newTask: Task = {
      title: this.taskFormGroup.value.title || '',
      description: this.taskFormGroup.value.title || '',
    }
    this.taskService.saveTask(newTask)
    .pipe(catchError((error: HttpErrorResponse) => {
      if(error.status === 400) {
        const messageError = error.error as MessageError;
        if(messageError.body.codigo === '400' && typeof messageError.body.mensaje === 'string') {
          this.openSnackBar(messageError.body.mensaje, "Cerrar");
        }
        this.loading.set(false);
        return throwError(() => new Error(""));
      }
      this.loading.set(false);
      return throwError(() => new Error("Error al guardar la tarea: " + error)); 
    }))
    .subscribe((result) => {
      this.openSnackBar("Tarea guardada correctamente", "Cerrar");
      this.taskService.getTasks();
      this.resetForm();
      this.dialogRef.close();
      this.loading.set(false);
    });
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  }
  resetForm() {
    this.taskFormGroup.reset();
  }
}
