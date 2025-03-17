import { Routes } from '@angular/router';
import { TaskComponent } from '@components/task/task.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { canAccesGuard } from './guards/can-acces.guard';
import { canAccessLoginGuard } from './guards/can-access-login.guard';

export const routes: Routes = [
    {path: '', component: LoginComponent, title: "Login", canActivate: [canAccessLoginGuard]},
    {path: '', component: NavbarComponent, children: [
        {path: 'tasks', component: TaskComponent, title: "Tasks", canActivate: [canAccesGuard]},
    ]},
];
