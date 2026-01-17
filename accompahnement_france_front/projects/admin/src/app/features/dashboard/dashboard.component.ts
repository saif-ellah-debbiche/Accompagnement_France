import { Component, OnInit } from '@angular/core';
import { CheckCircle2, Clock, Inbox, TrendingUp, LucideAngularModule, Loader, LucideIconConfig,  LucideIconData } from 'lucide-angular';
import { 
  CardComponent, 
  CardHeaderComponent, 
  CardTitleComponent, 
  CardContentComponent 
} from '../../shared/card';
import { Servicestatus } from '../../core/models/service-status.module';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../../shared/header/header.component";
import { DemandStatics } from '../../core/models/demand-statics.module';
import { AdminService } from '../../core/services/admin.service';
import { timeAgoFr } from '../../core/utils/functions';


interface RecentAct {
  title: string;
  time: string;
  id: string;
}
 interface StatCard {
  title: string;               
  value: number;                
  icon: LucideIconData;             
  description: string;
}
export interface ServicePopulaty {
  title: string;   
  count: number;   
  color: string;   
}
@Component({
  selector: 'app-dashboard',
  imports: [CardContentComponent, CardHeaderComponent, CardTitleComponent, CardComponent, LucideAngularModule, CommonModule, HeaderComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})


export class DashboardComponent implements OnInit {
readonly Inbox = Inbox;

title = "Tableau de bord";
description = "Bienvenue dans votre espace de gestion AccompagneFrance.";


demandStatics!:DemandStatics;
stats:StatCard[]=[];
recentActivity:RecentAct[]=[];
services: ServicePopulaty[] = [];

constructor(private adminService :AdminService){

}
ngOnInit(): void {
  this.adminService.getDemandStatics().subscribe({
  next:statics=>{
  this.stats = [
  {
    title: "Total Demandes",
    value: statics.totalDemands,
    icon: Inbox,
    description: statics.percentageChangeLastDays>0 ? `+${statics.percentageChangeLastDays} % par rapport au mois dernier`:"Aucun changement récent",
    
  },
  {
    title: "En attente",
    value:  statics.demandsInWaitingStatus,
    icon: Clock,
    description: "Nécessite une action rapide",
  },
 
  {
    title: "En cour de traitement",
    value: statics.demandsInProgressing,
    icon: Loader,
    description: "Aucun changement récent",
  },
   {
    title: "Traitées",
    value:  statics.closedDemands,
    icon: CheckCircle2,
    description: "Derniers 30 jours",
  },
]
this.recentActivity=statics.recentActivities.sort(
  (a, b) =>  new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
).map(demand=>{

  return {
    title:"Nouvelle demande reçue",
    time: timeAgoFr(demand.createdAt),
    id:demand.wantedServiceLabel
  }
});
const colors=['bg-blue-500','bg-emerald-500','bg-amber-500' ]
this.services=statics.popularServices.sort((serviceA, serviceB) => serviceB.demandCount - serviceA.demandCount).map((service,i)=>{
  
  return {
    title:service.serviceLabel,
    count:service.demandCount,
    color: i<colors.length ? colors[i] : colors[0]
  }
})


      }
    })
}





  // Services data
  

  getPercentage(count: number): number {
    const maxCount = Math.max(...this.services.map(s => s.count));
    return (count / maxCount) * 100;
  }
}
