import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Eye, EyeOff, Lock, LucideAngularModule } from 'lucide-angular';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-reset-password',
  imports: [CommonModule, FormsModule, RouterModule, LucideAngularModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent implements OnInit {
  readonly Lock = Lock;
  readonly Eye = Eye;
  readonly EyeOff = EyeOff;

  password: string = '';
  confirmPassword: string = '';
  showPassword: boolean = false;
  showConfirmPassword: boolean = false;
  isLoading: boolean = false;
  resetSuccess: boolean = false;
  errorMessage: string = '';
  token:string|null=null

  constructor(private route: ActivatedRoute,private router: Router,private authService:AuthService) {
  }
   ngOnInit(): void {
    this.token = this.route.snapshot.paramMap.get('token')!;
  
  }

  get hasUpperCase(): boolean {
    return /[A-Z]/.test(this.password);
  }

  get hasLowerCase(): boolean {
    return /[a-z]/.test(this.password);
  }

  get hasNumber(): boolean {
    return /[0-9]/.test(this.password);
  }
get hasSpecialChar(): boolean {
  return /[!@#$%^&*]/.test(this.password);
}
  get isPasswordValid(): boolean {
    return this.password.length >= 8 && 
           this.hasUpperCase && 
           this.hasLowerCase && 
           this.hasNumber &&
           this.hasSpecialChar &&
           this.password === this.confirmPassword;
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  async handleSubmit() {
    if(this.token==null){
      this.errorMessage = 'Le lien de réinitialisation du mot de passe est invalide.';
      return;
    }
    this.errorMessage = '';

    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Les mots de passe ne correspondent pas.';
      return;
    }

    if (!this.isPasswordValid) {
      this.errorMessage = 'Le mot de passe ne respecte pas tous les critères.';
      return;
    }

    this.isLoading = true;
    this.authService.resetPassword(this?.token||"",this.password ,this.confirmPassword).subscribe({
      next:response=>{
        this.isLoading=false;
        this.resetSuccess=true;
      },
      error:err=>{
        this.isLoading=false;
        this.resetSuccess=false;
        this.errorMessage='mpossible de réinitialiser le mot de passe. Le lien n’est plus valide.';
      }
    })
  }

}
