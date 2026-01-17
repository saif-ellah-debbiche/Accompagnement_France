import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withInMemoryScrolling } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { baseURlInterceptor } from '../../../admin/src/app/core/interceptors/base-url.interceptor';
import { provideLucideIconsConfig } from './icons.provider';


export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideLucideIconsConfig(),
    provideHttpClient(
      withInterceptors([
        baseURlInterceptor,
      ],
    )),      
    provideRouter(routes,
       withInMemoryScrolling({
        scrollPositionRestoration: 'top', // 👈 always go to top
        anchorScrolling: 'enabled'
      })),
    
  ],
    
};
