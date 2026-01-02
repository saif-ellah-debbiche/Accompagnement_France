import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { SITE_CONFIG } from '../../../core/config/site.config';
import { Service } from '../../../core/models/service.model';
import { ButtonComponent } from "../../../shared/button/button.component";
import { LucideAngularModule } from "lucide-angular";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-services',
  imports: [ButtonComponent, LucideAngularModule,CommonModule],
  templateUrl: './services.component.html',
  styleUrl: './services.component.scss'
})
export class ServicesComponent {
 @ViewChild("servicesSection") servicesSectionRef!:ElementRef;
  @Output() scrollToContact= new EventEmitter<void>();
  scrollTo(){
    this.servicesSectionRef.nativeElement.scrollIntoView({ behavior: 'smooth' });
  }
  scrollToContactFn(){
    this.scrollToContact.emit();
  }
  



services : Service[] =  SITE_CONFIG.services;

}
