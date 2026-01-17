import { Routes } from '@angular/router';
import { authGuard } from '../../core/guards/auth.guard';

export const AUTH_ROUTES: Routes = [
   {
    path: 'login',
    loadComponent: () =>
      import('./login/login.component')
        .then(m => m.LoginComponent)
  },
  {
    path: 'change-password',
    canActivate: [authGuard],        // logged in
    loadComponent: () =>
      import('./change-password/change-password.component')
        .then(m => m.ChangePasswordComponent)
  },
  {
    path: 'reset-password/:token',
    loadComponent: () =>
      import('./reset-password/reset-password.component')
        .then(m => m.ResetPasswordComponent)
  },
  {
    path: 'forget-password',
    loadComponent: () =>
      import('./forget-password/forget-password.component')
        .then(m => m.ForgetPasswordComponent)
  },
];