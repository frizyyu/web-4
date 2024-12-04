import { Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';

import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {StartComponent} from './start/start.component'
import {ProfileComponent} from './profile/profile.component'

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'start', component: StartComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];
