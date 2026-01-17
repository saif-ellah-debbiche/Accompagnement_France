import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);
 
  const token = auth.getAccessToken();


  
 if (auth.getMustChangePassword() && state.url !== '/auth/change-password') {
    router.navigate(['/auth/change-password']);
    return false;
  }


  console.log("user must change password " + auth.getMustChangePassword() )
  
  if (token) {
    return true; // user has access token → allow route
  }

  // No token → redirect to login
  router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
  return false;
};
