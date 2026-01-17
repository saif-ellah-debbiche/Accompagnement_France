import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ArrowLeft, Eye, EyeOff, Lock, LucideAngularModule } from 'lucide-angular';
import { AuthService } from '../../../core/services/auth.service';
import { ChangePasswordRequest } from '../../../core/models/change-password-request.module';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-change-password',
  imports: [CommonModule, FormsModule, LucideAngularModule],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.scss'
})
export class ChangePasswordComponent {
  readonly Lock = Lock;
  readonly Eye = Eye;
  readonly EyeOff = EyeOff;
  readonly    arrowLeft=ArrowLeft;

  mustChangePassword = false;
  currentPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';
  showCurrentPassword: boolean = false;
  showNewPassword: boolean = false;
  showConfirmPassword: boolean = false;
  isLoading: boolean = false;
  errorMessage: string = '';
  successMessage: string = '';


  constructor(private router:Router,private authService:AuthService,private toastService:ToastService){
    this.mustChangePassword = authService.getMustChangePassword();
  }
  



  get hasUpperCase(): boolean {
    return /[A-Z]/.test(this.newPassword);
  }

  get hasLowerCase(): boolean {
    return /[a-z]/.test(this.newPassword);
  }

  get hasNumber(): boolean {
    return /[0-9]/.test(this.newPassword);
  }
get hasSpecialChar(): boolean {
  return /[!@#$%^&*]/.test(this.newPassword);
}
  get isPasswordValid(): boolean {
    return this.currentPassword.length > 0 &&
           this.newPassword.length >= 8 && 
           this.hasUpperCase && 
           this.hasLowerCase && 
           this.hasNumber &&
           this.hasSpecialChar &&
           this.newPassword === this.confirmPassword;
  }

  toggleCurrentPassword() {
    this.showCurrentPassword = !this.showCurrentPassword;
  }

  toggleNewPassword() {
    this.showNewPassword = !this.showNewPassword;
  }

  toggleConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  handleBack() {
    // Reset form
    this.router.navigate(["settings"])
  }

  async handleSubmit() {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.currentPassword) {
      this.errorMessage = 'Veuillez entrer votre mot de passe actuel.';
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.errorMessage = 'Les nouveaux mots de passe ne correspondent pas.';
      return;
    }

    if (this.currentPassword === this.newPassword) {
      this.errorMessage = 'Le nouveau mot de passe doit être différent de l\'ancien.';
      return;
    }

    if (!this.isPasswordValid) {
      this.errorMessage = 'Le mot de passe ne respecte pas tous les critères.';
      return;
    }

    this.isLoading = true;

    const changePasswordRequest : ChangePasswordRequest= {
      oldPassword:this.currentPassword ,
      newPassword:this.newPassword,
      newPasswordConfirm:this.confirmPassword
      
    }
    this.authService.changePassword(changePasswordRequest).subscribe({
      next:res=>{
        this.isLoading = false;
        this.toastService.success( 'Mot de passe mis à jour','Votre mot de passe a été modifié avec succès.');
        if(this.mustChangePassword){
          this.router.navigate(["/dashboard"])
        }else{
          this.router.navigate(["/settings"])

        }
      },
      error:err=>{
        this.isLoading = false;
        this.errorMessage = "Une erreur est survenue lors de la modification du mot de passe. Veuillez réessayer.";
      }
    })

  }
}
