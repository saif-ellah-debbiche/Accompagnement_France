import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Eye, EyeOff, Lock, LogIn, LucideAngularModule, Mail } from 'lucide-angular';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterModule, LucideAngularModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
 host: {
    class: "!bg-background"
  }
})
export class LoginComponent implements OnInit  {
  returnUrl: string = '/dashboard'; // default


readonly Mail = Mail;
  readonly Lock = Lock;
  readonly Eye = Eye;
  readonly EyeOff = EyeOff;
  readonly LogIn = LogIn;

  email: string = '';
  password: string = '';
  rememberMe: boolean = false;
  showPassword: boolean = false;
  errorMessage: string = '';
  isLoading:boolean= false;

  constructor(private router: Router,    private route: ActivatedRoute,private authService:AuthService) {}

   ngOnInit() {
    // read query param
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/dashboard';
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  async handleLogin() {
    this.isLoading = true;
    this.errorMessage = '';

    // Simulate API call
    this.authService.login({email:this.email,password:this.password}).subscribe({
  next: res => {
    // Navigate to dashboard or whatever
    this.router.navigate([this.returnUrl]);
     this.isLoading = false;
    },
    error: err => {
      console.error('Login failed', err);
      // Show error message to user
      this.errorMessage = 'Invalid email or password';
      this.isLoading = false;
  }
});
  }
}
