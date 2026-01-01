import { Component, EventEmitter, Output } from '@angular/core';
import { ChevronDown, LucideAngularModule } from 'lucide-angular';
import { SITE_CONFIG } from '../../../core/config/site.config';
import { ButtonComponent } from "../../../shared/button/button.component";

@Component({
  selector: 'app-hero-section',
  imports: [ButtonComponent, LucideAngularModule],
  templateUrl: './hero-section.component.html',
  styleUrl: './hero-section.component.scss'
})
export class HeroSectionComponent {
icons = { ChevronDown };
  @Output() scrollToContact = new EventEmitter<void>();
  @Output() scrollToServices = new EventEmitter<void>();
  
   hero = SITE_CONFIG.heroSectionData;
  
  onClickContact() {
    this.scrollToContact.emit();
  }

  onClickServices() {
    this.scrollToServices.emit();
  }
}
