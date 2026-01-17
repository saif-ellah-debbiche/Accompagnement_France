import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Briefcase, FileText, Home, LogOut, LucideAngularModule, Menu, Settings, Users } from "lucide-angular";
import { NavItem } from '../../core/models/nav-item.module';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-left-side-bar',
  imports: [LucideAngularModule,CommonModule,RouterModule],
  templateUrl: './left-side-bar.component.html',
  styleUrl: './left-side-bar.component.scss'
})
export class LeftSideBarComponent {
  readonly FileText = FileText;
  readonly LogOut = LogOut;
  readonly Menu = Menu;
  @Output() colapsedEvent=new EventEmitter<void>();

  @Input() navItems: NavItem[] = [
    { name: 'Dashboard', href: '/dashboard', icon: Home },
    { name: 'Demandes', href: '/demands', icon: Briefcase },
    { name: 'Settings', href: '/settings', icon: Settings },
  ];

  isCollapsed = false;

  constructor(private router: Router,private authService:AuthService) {}

  toggleSidebar() {
    this.colapsedEvent.emit();
    this.isCollapsed = !this.isCollapsed;
  }
  logout(){
    console.log("loggin out")
    this.authService.logout().subscribe({
      next:res=>{
        console.log("reaching here ")
        this.router.navigateByUrl("/auth/login")},
        error:err=>{
          this.authService.clearSession();
          this.router.navigateByUrl("/auth/login");
          console.log(err)}

    });
  }
}
