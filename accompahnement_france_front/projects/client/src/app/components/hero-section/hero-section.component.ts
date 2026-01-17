import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { ChevronDown, LucideAngularModule } from 'lucide-angular';
import { SITE_CONFIG } from '../../core/config/site.config';
import { ButtonComponent } from "../../shared/button/button.component";

@Component({
  selector: 'app-hero-section',
  imports: [ButtonComponent, LucideAngularModule],
  templateUrl: './hero-section.component.html',
  styleUrl: './hero-section.component.scss'
})
export class HeroSectionComponent {
icons = { ChevronDown };
  
  @Output() scrollToServices = new EventEmitter<void>();
  
   hero = SITE_CONFIG.heroSectionData;
 
@ViewChild("heroSectionComponent") teamSectionRef!:ElementRef;
  scrollTo(){
    this.teamSectionRef.nativeElement.scrollIntoView({ behavior: 'smooth' });
  }
  onClickServices() {
    this.scrollToServices.emit();
  }
}
