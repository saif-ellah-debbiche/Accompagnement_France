import { Component, Input } from '@angular/core';
import { CardComponent } from "../../../../../admin/src/app/shared/card";
import { LucideAngularModule } from "lucide-angular";
import { Service } from '../../core/models/service.model';
import { CommonModule } from '@angular/common';
import { UserCheck, RefreshCw, Flag, Home, Users, AlertCircle, FileText, Briefcase } from 'lucide-angular';

@Component({
  selector: 'app-service-card',
  imports: [CommonModule, LucideAngularModule, CardComponent],
  templateUrl: './service-card.component.html',
  styleUrl: './service-card.component.scss'
})
export class ServiceCardComponent {
@Input() service!: Service;
 iconMap = {
    UserCheck: UserCheck,
    RefreshCw: RefreshCw,
    Flag: Flag,
    Home: Home,
    Users: Users,
    AlertCircle: AlertCircle,
    FileText: FileText,
    Briefcase: Briefcase
  };
}
