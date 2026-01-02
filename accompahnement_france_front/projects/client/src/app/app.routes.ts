import { Routes } from '@angular/router';

export const routes: Routes = [ {
    path: '',
    loadComponent: () =>
      import('./public/pages/home/home.component').then(c => c.HomeComponent)
  },
 {
    path: 'privacy-policy',
    loadComponent: () =>
      import('./public/pages/privacy-policy/privacy-policy.component').then(c => c.PrivacyPolicyComponent)
  },
 {
    path: 'legal-notice',
    loadComponent: () =>
      import('./public/pages/legal-notice/legal-notice.component').then(c => c.LegalNoticeComponent)
  },
 {
    path: 'cvg',
    loadComponent: () =>
      import('./public/pages/cgv/cgv.component').then(c => c.CgvComponent)
  },
];
