import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { LucideAngularModule, Mail } from 'lucide-angular';
import { AdminService } from '../../../core/services/admin.service';

@Component({
  selector: 'app-forget-password',
  imports: [CommonModule, FormsModule, RouterModule, LucideAngularModule],
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.scss'
})
export class ForgetPasswordComponent {
readonly Mail = Mail;

  email: string = '';
  isLoading: boolean = false;
  emailSent: boolean = false;
  errorMessage=null;

  constructor(private router: Router,private adminService:AdminService) {}

  async handleSubmit() {
     this.errorMessage=null;
    this.isLoading=true;
    if(!this.email) return;
    this.adminService.forgetPassword(this.email).subscribe({
      next:response=>{
      this.isLoading=false;
      this.emailSent=true;
      },
      error:err=>{
        this.isLoading=false;
        console.log(err);
        this.errorMessage=err.error.message;

      }
    });
  }
}
