import { Component, inject } from '@angular/core';
import { ServiceCardComponent } from "../../components/service-card/service-card.component";
import { UserCheck, RefreshCw, Flag, Home, Users, AlertCircle, FileText, Briefcase, LucideAngularModule } from 'lucide-angular';

import { Service } from '../../core/models/service.model';
import { ClientService } from '../../core/services/client.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from "@angular/router";
@Component({
  selector: 'app-services-page',
  imports: [ServiceCardComponent, CommonModule, LucideAngularModule, RouterLink],
  templateUrl: './services-page.component.html',
  styleUrl: './services-page.component.scss'
})
export class ServicesPageComponent {
  clientService=inject(ClientService);
  services$ = this.clientService.services$;
}
