import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { NavBarService } from '../../core/services/nav-bar.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  imports: [CommonModule, RouterModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent  {
 @Output() scrollToContact = new EventEmitter<void>();
  navService = inject(NavBarService);
  activeSection$ = this.navService.scrollToSection$;
  constructor(    private router: Router,
) {

  }
  activeSection: string | null = null;
  isHomePage = false;
  scrollToSection(sectionId: string): void {
  this.navService.scrollTo(sectionId);
}
  goToSection(section: string) {
    const currentUrl = this.router.url;
     if (currentUrl !== '/' && currentUrl !== '/home') {
      // Navigate to home first, then scroll
      this.router.navigate(['/home']).then(() => {
        setTimeout(() => {
          this.navService.scrollTo(section);
        }, 300); // Small delay to let the page load
      });
    } else {
      // Already on home, just scroll
      this.navService.scrollTo(section);
    }
  this.navService.scrollTo(section)
  }
}
