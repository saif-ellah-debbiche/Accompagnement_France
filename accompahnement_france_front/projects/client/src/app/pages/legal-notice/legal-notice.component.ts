import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ArrowLeft, LucideAngularModule } from 'lucide-angular';
import { ButtonComponent } from "../../shared/button/button.component";
import { SITE_CONFIG } from '../../core/config/site.config';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-legal-notice',
  imports: [ButtonComponent,CommonModule, LucideAngularModule],
  templateUrl: './legal-notice.component.html',
  styleUrl: './legal-notice.component.scss'
})
export class LegalNoticeComponent {
 arrowLeft=ArrowLeft;
  constructor(private router:Router){
  }

  onClickback(){
    this.router.navigate(["/"])
  }
}
