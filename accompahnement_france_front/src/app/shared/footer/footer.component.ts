import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent {
  @Output() scrollToTeam = new EventEmitter<void>();
  @Output() scrollToContact = new EventEmitter<void>();
  currentYear: number = new Date().getFullYear();
  constructor(private router:Router){

  } 

  onClickTeam() {
    this.scrollToTeam.emit();
  }
  onClickService(){
    this.scrollToContact.emit();

  }

  goToPrivacyAndPolicy(){
    this.router.navigate(['/privacy-policy']);
  }
  goTolegalNotice(){
    this.router.navigate(['/legal-notice']);
  }
  goToCVG(){
    this.router.navigate(['/cvg']);
  }

  
}
