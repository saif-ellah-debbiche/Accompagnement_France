import { Component, OnInit } from '@angular/core';
import { CheckCircle2, Clock, Eye, MoreHorizontal, Search, LucideAngularModule, Phone } from 'lucide-angular';
import { Demand } from '../../core/models/demand.module';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "../../shared/header/header.component";
import { AdminService } from '../../core/services/admin.service';
import { timeAgoFr } from '../../core/utils/functions';
import { ConfirmationDialogComponent } from "../../shared/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: 'app-demands',
  imports: [CommonModule, LucideAngularModule, FormsModule, HeaderComponent, ConfirmationDialogComponent],
  templateUrl: './demands.component.html',
  styleUrl: './demands.component.scss',
})
export class DemandsComponent implements OnInit  {
  title = "Gestion des demandes";
 description = "Consultez et gérez toutes les demandes de vos clients.";
  readonly Search = Search;
  readonly MoreHorizontal = MoreHorizontal;
  readonly Eye = Eye;
  readonly Clock = Clock;
  readonly CheckCircle2 = CheckCircle2;
  readonly  Phone = Phone;


  retentionPeriod: string = '90';
  cleanupAction: string = "ANONYMIZE";
  
  showSubmitDialog=false;
  dialogTitle='';
  dialogDescription='';
  dialogConfirm='Confirmer';
  dialogContent='';
  demandToClose:Demand|null=null;
  // State
  demands: Demand[] = [];
  filteredDemands: Demand[] = [];
  searchQuery: string = '';
  statusFilter:'all'|'CLOSED' | 'IN_PROGRESS' | 'CONTACTED'|'NEW'='all';
  openDropdownId: string | null = null;
  selectedDemand: Demand | null = null;
  


constructor(private adminService:AdminService){}
ngOnInit() {
    // Initialize with sample data
    this.adminService.getDemands().subscribe({
      next:response=>{
        console.log(response);
        this.demands =response.sort(
  (a, b) =>  new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
);
         this.filteredDemands = [...this.demands];
       },
       error:err=>{
        console.log(err)
       }
    })
    this.adminService.getRetentionParams().subscribe({
      next:res=>{
        console.log(res);
        this.retentionPeriod = `${res.daysToRetainAfterClosure}`;
        this.cleanupAction=res.actionType;
      },
      error:err=>console.log(err)
    }) 
   
  }
  filterDemands() {
    this.filteredDemands = this.demands.filter(demand => {
      const matchesSearch = 
        `${demand.firstName} ${demand.lastName}`.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        demand.email.toLowerCase().includes(this.searchQuery.toLowerCase());
      
      console.log('matchesSearch '+matchesSearch+" searchQuery "+this.searchQuery)
      console.log('current status filter '+this.statusFilter)
      const matchesStatus = this.statusFilter === 'all' || demand.status === this.statusFilter;
      
      return matchesSearch && matchesStatus;
    });
  }

  setStatusFilter(status: 'all'|'CLOSED' | 'IN_PROGRESS' | 'CONTACTED'|'NEW') {
    this.statusFilter = status;
    this.filterDemands();
  }

  getFilterButtonClass(filter: 'all'|'CLOSED' | 'IN_PROGRESS' | 'CONTACTED'|'NEW'): string {
    const baseClasses = 'px-3 py-2 text-sm font-medium rounded-md transition-colors';
    if (this.statusFilter === filter) {
      return `${baseClasses} bg-secondary text-secondary-foreground`;
    }
    return `${baseClasses} hover:bg-accent hover:text-accent-foreground`;
  }
  getDate(createdAt:string){
    return timeAgoFr(createdAt);
  }
  getStatusBadge(status: string): string {
    const baseClasses = 'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border';
    
    switch (status) {
      case 'NEW':
        return `<span class="${baseClasses} bg-amber-500/10 text-amber-500 border-amber-500/20">En attente</span>`;
      case 'IN_PROGRESS':
        return `<span class="${baseClasses} bg-blue-500/10 text-blue-500 border-blue-500/20">En cours</span>`;
      case 'CLOSED':
        return `<span class="${baseClasses} bg-emerald-500/10 text-emerald-500 border-emerald-500/20">Terminé</span>`;
      case 'CONTACTED':
        return `<span class="${baseClasses} bg-indigo-500/10 text-indigo-500 border-indigo-500/20">Contacté</span>`;
      default:
        return `<span class="${baseClasses}">Inconnu</span>`;
    }
  }

  toggleDropdown(demandId: string, index: number) {
    this.openDropdownId = this.openDropdownId === demandId ? null : demandId;
  }
   getDropdownPositionClass(index: number): string {
    // Calculate how many rows from the bottom
    const rowsFromBottom = this.filteredDemands.length - index - 1;
    
    // If we're in the last 2 rows, position dropdown ABOVE the button
    if (rowsFromBottom < 3) {
      return 'bottom-full mb-2'; // Opens upward
    }
    
    // Otherwise, position dropdown BELOW the button (default)
    return 'mt-2'; // Opens downward
  }
  handleCloseDemand(){
    this.handleChangeDemandStatus('CLOSED');
  }
handleChangeDemandStatus(status:'CLOSED' | 'IN_PROGRESS' | 'CONTACTED'|'NEW'){
  if(this.demandToClose){
    this.adminService.changeDemandStatus(this.demandToClose.id,status).subscribe({
      next:response=>{
      if(this.demandToClose ) this.demandToClose.status = status;
      this.filterDemands();
      this.showSubmitDialog=false;
      this.demandToClose=null;
      },
      error:err=>{
        console.log(err);
        this.showSubmitDialog=false;
        this.demandToClose=null;
      }
    });
  }
}
  viewDetails(demand: Demand) {
    this.selectedDemand = demand;
    this.openDropdownId = null;
  }

  closeDialog() {
    this.selectedDemand = null;
  }

  updateStatus(demandId: string, newStatus: 'CLOSED' | 'IN_PROGRESS' | 'CONTACTED'|'NEW') {
    const demand = this.demands.find(d => d.id === demandId);
    if (demand) {
      this.demandToClose=demand;
      if(newStatus=='CLOSED'){
          this.dialogTitle="Clôturer la demande";
          this.dialogDescription="Vous êtes sur le point de clôturer cette demande.";
          this.dialogContent=this.cleanupAction === 'ANONYMIZE'?`
              Lorsque la demande est clôturée, les données personnelles associées (nom, prénom, numéro de téléphone, etc.) seront supprimées automatiquement après un délai de ${this.retentionPeriod} jours à compter de la date de clôture.

              La demande sera ensuite conservée uniquement à des fins statistiques, conformément à la réglementation en vigueur sur la protection des données.
              `:`
              Une fois la demande clôturée, elle ne pourra plus être modifiée.
              Elle sera automatiquement supprimée  ${this.retentionPeriod} jours après la date de clôture,
              conformément à la politique de conservation des données.
          `;
          this.showSubmitDialog=true;
      }else{
      this.handleChangeDemandStatus(newStatus);
      }
     
    }
    this.openDropdownId = null;
  }
}
