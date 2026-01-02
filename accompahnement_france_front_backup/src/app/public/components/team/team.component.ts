import { Component, ElementRef, ViewChild } from '@angular/core';
import { LucideAngularModule, User } from "lucide-angular";
import { TeamMember } from '../../../core/models/team_member.module';
import { SITE_CONFIG } from '../../../core/config/site.config';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-team',
  imports: [LucideAngularModule,CommonModule],
  templateUrl: './team.component.html',
  styleUrl: './team.component.scss'
})
export class TeamComponent {
 readonly User = User;  
 team: TeamMember[] =SITE_CONFIG.team; 
 @ViewChild("teamSection") teamSectionRef!:ElementRef;
  scrollTo(){
    this.teamSectionRef.nativeElement.scrollIntoView({ behavior: 'smooth' });
  }
}
