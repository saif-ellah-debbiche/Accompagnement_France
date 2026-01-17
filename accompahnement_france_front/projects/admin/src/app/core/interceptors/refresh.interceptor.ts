import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { catchError, filter, switchMap, take, tap, throwError } from 'rxjs';

export const refreshInterceptor: HttpInterceptorFn = (req, next) => {
const authService = inject(AuthService);


if (req.url.includes('/auth/refresh')||req.url.includes('/auth/login')) {
    return next(req);
  }

 return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.log(error)
      console.log(error.status)
      

      if (error.status >= 500) {
            // Log it, display generic error, and then stop propagation (no refresh attempt)
            console.error("Backend 500 Error:", error.error.message);
            return throwError(() => error); 
        }
      if (error.status !== 401) {
        return throwError(() => error);
      }

      // 🔒 If refresh already running → wait
      
      if (authService.isRefreshing()) {
        return authService.waitForRefresh().pipe(
          filter(token => token !== null),
          take(1),
          switchMap(newToken => {
              const retryReq = req.clone({
                setHeaders: { Authorization: `Bearer ${newToken}` }
              });
              return next(retryReq);
            })
          );
        }

      // 🔄 Start refresh
      authService.startRefresh();

      console.log("refreshing the token")
      return authService.refresh().pipe(
          tap(res => {
            console.log("Refresh succeeded. New token: ", res.accessToken);
            authService.setAccessToken(res.accessToken, res.mustChangePassword);
          }),
          // CRITICAL FIX 2: Clone request with the new token from the response
          switchMap((res) => {
            authService.endRefresh();
            const retryReq = req.clone({
              setHeaders: { Authorization: `Bearer ${res.accessToken}` }
            });
            return next(retryReq);
          }),
        catchError(err => {
          authService.endRefresh();
          authService.clearSession(); // logout
          return throwError(() => err);
        })
      );
    })
  );
};
