import { Routes } from '@angular/router';

export const routes: Routes = [
   {
    path: '',
    loadComponent: () =>
      import('./layout/layout.component').then(c => c.LayoutComponent),
  children: [
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
      {
    path: 'home',
    loadComponent: () =>
      import('./pages/home/home.component').then(c => c.HomeComponent)
  },
     {
    path: 'services',
    loadComponent: () =>
      import('./pages/services-page/services-page.component').then(c => c.ServicesPageComponent)
  },
 {
    path: 'privacy-policy',
    loadComponent: () =>
      import('./pages/privacy-policy/privacy-policy.component').then(c => c.PrivacyPolicyComponent)
  },
 {
    path: 'legal-notice',
    loadComponent: () =>
      import('./pages/legal-notice/legal-notice.component').then(c => c.LegalNoticeComponent)
  },
 {
    path: 'cvg',
    loadComponent: () =>
      import('./pages/cgv/cgv.component').then(c => c.CgvComponent)
  },
    ]
  }
   
];
