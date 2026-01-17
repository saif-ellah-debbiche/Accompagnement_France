import { Component, OnInit } from '@angular/core';
import { CardComponent, CardHeaderComponent, CardTitleComponent, CardContentComponent } from "../../shared/card";
import { Clock, LucideAngularModule, Moon, Save, ShieldCheck, Sun } from "lucide-angular";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConfirmationDialogComponent } from "../../shared/confirmation-dialog/confirmation-dialog.component";
import { ToastService } from '../../core/services/toast.service';
import { AdminService } from '../../core/services/admin.service';
import { ThemeService } from '../../core/services/theme.service';

@Component({
  selector: 'app-settings',
  imports: [
    CommonModule,
    FormsModule,
    LucideAngularModule,
    CardComponent,
    CardHeaderComponent,
    CardTitleComponent,
    CardContentComponent,
    ConfirmationDialogComponent
],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss',
  
})
export class SettingsComponent {
 readonly Save = Save;
  readonly Sun = Sun;
  readonly Moon = Moon;
  readonly Clock = Clock;
  readonly ShieldCheck = ShieldCheck;

  dialogText={
    title:'',
    description:'',
    content:''
  }
  showSaveDialog = false;
  showGenericDialog = false;
  isSaving = false;

  theme!: 'dark'|'light';
  retentionPeriod: string = '90';
  cleanupAction: string = "ANONYMIZE";

  constructor(private toastService:ToastService,private adminService:AdminService,private themeService:ThemeService){
    this.theme=themeService.current;
    adminService.getRetentionParams().subscribe({
      next:res=>{
        console.log(res);
        this.retentionPeriod = `${res.daysToRetainAfterClosure}`;
        this.cleanupAction=res.actionType;
      },
      error:err=>console.log(err)
    })
  }
  get dialogTitle(): string {
  return this.cleanupAction === 'ANONYMIZE'
    ? 'Archiver les demandes'
    : 'Supprimer les demandes';
}
get dialogDescription(): string {
  return this.cleanupAction === 'ANONYMIZE'
    ? 'Les données personnelles seront anonymisées'
    : 'Les demandes seront supprimées ';
}
get dialogContent(): string {
  return this.cleanupAction === 'ANONYMIZE'
    ? `Les données personnelles seront anonymisées, puis la demande sera supprimée automatiquement ${this.retentionPeriod}  jours après sa clôture.`
    :`Les demandes seront supprimées automatiquement ${this.retentionPeriod} jours après leur clôture, ainsi que toutes les données associées.` ;
}
get dialogConfirm(): string {
  return this.cleanupAction === 'ANONYMIZE' ? 'Anonymiser' : 'Supprimer' 
}
onThemeChange(theme: 'light' | 'dark') {
    this.themeService.setTheme(theme);
  }
  openSaveDialog() {
    this.showSaveDialog = true;
  }
  // State
  
  // Toast state
  showToast: boolean = false;
  toastTitle: string = '';
  toastDescription: string = '';

  async handleSave() {
   this.isSaving = true;
    this.adminService.setRetentionPolicy(this.retentionPeriod,this.cleanupAction).subscribe({
      next:response=>{
         this.isSaving = false;
         this.showSaveDialog = false;
         this.toastTitle = 'Paramètres enregistrés';
         this.toastDescription = 'Vos préférences ont été mises à jour avec succès.';
        this.toastService.success(this.toastTitle, this.toastDescription )
      },
      error:err=>{
        this.toastTitle = 'Les paramètres n’ont pas été modifiés.';
         this.toastDescription = 'Les paramètres n’ont pas été modifiés. Une erreur est survenue.';
        this.toastService.error(this.toastTitle, this.toastDescription )
        console.log(err)
      }
    })
  }
}
