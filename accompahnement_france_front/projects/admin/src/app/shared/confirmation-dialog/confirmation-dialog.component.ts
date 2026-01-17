import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AlertTriangle, X, LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-confirmation-dialog',
  imports: [LucideAngularModule,CommonModule],
  templateUrl: './confirmation-dialog.component.html',
  styleUrl: './confirmation-dialog.component.scss'
})
export class ConfirmationDialogComponent {
readonly AlertTriangle = AlertTriangle;
readonly X = X;
  // Inputs
@Input() isOpen: boolean = false;
@Input() title: string = '';
@Input() description: string = '';
@Input() confirmText: string = 'Confirmer';
@Input() cancelText: string = 'Annuler';
@Input() loadingText: string = 'Chargement...';
@Input() isDestructive: boolean = false; // Red button for dangerous actions
@Input() showWarning: boolean = true;
@Input() showContent: boolean = false; // Show ng-content slot
@Input() isLoading: boolean = false;

  // Default texts
  defaultTitle: string = 'Êtes-vous sûr ?';
  defaultDescription: string = 'Cette action nécessite votre confirmation. Veuillez vérifier avant de continuer.';

  // Outputs
  @Output() confirm = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();

  onConfirm() {
    if (!this.isLoading) {
      this.confirm.emit();
    }
  }

  onCancel() {
    if (!this.isLoading) {
      this.cancel.emit();
    }
  }

  getConfirmButtonClass(): string {
    const baseClasses = 'px-4 py-2 text-sm font-medium rounded-md transition-colors disabled:opacity-50 disabled:cursor-not-allowed inline-flex items-center cursor-pointer' ;
    
    if (this.isDestructive) {
      return `${baseClasses} bg-red-500 text-white hover:bg-red-600`;
    }
    
    return `${baseClasses} bg-primary text-primary-foreground hover:bg-primary/90`;
  }
}
