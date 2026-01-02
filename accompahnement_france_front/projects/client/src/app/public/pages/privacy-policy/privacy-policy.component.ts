import { Component } from '@angular/core';
import { SITE_CONFIG } from '../../../core/config/site.config';
import { ArrowLeft, LucideAngularModule } from 'lucide-angular';
import { Router } from '@angular/router';
import { ButtonComponent } from "../../../shared/button/button.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-privacy-policy',
  imports: [ButtonComponent, LucideAngularModule,CommonModule],
  templateUrl: './privacy-policy.component.html',
  styleUrl: './privacy-policy.component.scss'
})
export class PrivacyPolicyComponent {
 config = SITE_CONFIG;
  arrowLeft=ArrowLeft;
  constructor(private router:Router){

  }
  onClickback(){
    this.router.navigate(["/"])
  }
}
