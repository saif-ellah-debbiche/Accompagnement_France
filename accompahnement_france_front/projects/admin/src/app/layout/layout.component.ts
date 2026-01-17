import { Component } from '@angular/core';
import { LeftSideBarComponent } from "./left-side-bar/left-side-bar.component";
import { RouterModule } from "@angular/router";
import { BarChart, Briefcase, FileText, Home, Settings, Users } from 'lucide-angular';

@Component({
  selector: 'app-layout',
  imports: [LeftSideBarComponent, RouterModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
sidebarCollapsed = false;
    navigationItems = [
     { name: 'Dashboard', href: '/dashboard', icon: Home },
    { name: 'Demandes', href: '/demands', icon: Briefcase },
    { name: 'Settings', href: '/settings', icon: Settings },
  ];
  title = 'admin';


  colapseSideBar(){
    console.log(this.sidebarCollapsed);
    this.sidebarCollapsed=!this.sidebarCollapsed;
  }
}
