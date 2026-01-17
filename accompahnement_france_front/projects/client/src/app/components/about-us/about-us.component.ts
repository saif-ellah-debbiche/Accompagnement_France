import { Component, ElementRef, ViewChild } from '@angular/core';
import { Heart, LucideAngularModule, Shield, Users } from 'lucide-angular';

@Component({
  selector: 'app-about-us',
  imports: [LucideAngularModule],
  templateUrl: './about-us.component.html',
  styleUrl: './about-us.component.scss'
})
export class AboutUsComponent {
 readonly ShieldIcon = Shield;
  readonly HeartIcon = Heart;
  readonly UsersIcon = Users;
  @ViewChild('aboutSertion') aboutSectionRef!: ElementRef;
  scrollTo() {
    this.aboutSectionRef.nativeElement.scrollIntoView({ behavior: 'smooth' });
  }

}
