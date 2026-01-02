import { Component } from '@angular/core';
import { SITE_CONFIG } from '../../../core/config/site.config';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ButtonComponent } from "../../../shared/button/button.component";
import { ArrowLeft, LucideAngularModule } from "lucide-angular";

@Component({
  selector: 'app-cgv',
  imports: [CommonModule, ButtonComponent, LucideAngularModule],
  templateUrl: './cgv.component.html',
  styleUrl: './cgv.component.scss'
})
export class CgvComponent {
   arrowLeft=ArrowLeft;

  cgv = SITE_CONFIG.legal.cgv;
  constructor(private router:Router){
  }

  onClickback(){
    this.router.navigate(["/"])
  }
}
