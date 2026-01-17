import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { SITE_CONFIG } from '../../core/config/site.config';
import { Service } from '../../core/models/service.model';
import { ButtonComponent } from "../../shared/button/button.component";
import { LucideAngularModule } from "lucide-angular";
import { CommonModule } from '@angular/common';
import { ClientService } from '../../core/services/client.service';
import { RouterLink } from "@angular/router";
import { UserCheck, RefreshCw, Flag, Home, Users, AlertCircle, FileText, Briefcase } from 'lucide-angular';
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-services',
  imports: [ButtonComponent, LucideAngularModule, CommonModule, RouterLink],
  templateUrl: './services.component.html',
  styleUrl: './services.component.scss'
})
export class ServicesComponent {
    services$!: Observable<Service[]>;


constructor(private clientService:ClientService){3

  this.services$ = this.clientService.services$.pipe(
      map(services => {
        if (services.length >= 6) {
          return services.slice(0, 6);
        } else if (services.length >= 3) {
          return services.slice(0, 3);
        } else {
          return services;
        }
      })
    );
}
   @ViewChild("servicesSection") servicesSectionRef!:ElementRef;
  @Output() scrollToContact= new EventEmitter<void>();
  scrollTo(){
    this.servicesSectionRef.nativeElement.scrollIntoView({ behavior: 'smooth' });
  }
  scrollToContactFn(){
    this.scrollToContact.emit();
  }
  





}
