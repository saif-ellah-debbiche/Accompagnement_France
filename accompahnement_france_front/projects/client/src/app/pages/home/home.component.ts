import { AfterViewInit, Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { ContactUsComponent } from '../../components/contact-us/contact-us.component';
import { ServicesComponent } from '../../components/services/services.component';
import { HeroSectionComponent } from "../../components/hero-section/hero-section.component";
import { AboutUsComponent } from "../../components/about-us/about-us.component";
import { FooterComponent } from "../../shared/footer/footer.component";
import { TeamComponent } from "../../components/team/team.component";
import { HeaderComponent } from "../../shared/header/header.component";
import { NavBarService } from '../../core/services/nav-bar.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
@Component({
  selector: 'app-home',
  imports: [HeroSectionComponent, ContactUsComponent, AboutUsComponent, ServicesComponent, TeamComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent  {
  @ViewChild(ContactUsComponent) contactUsComponent!: ContactUsComponent;
  @ViewChild(ServicesComponent) servicesComponent!:ServicesComponent;
  @ViewChild(TeamComponent) TeamComponent!:TeamComponent;
  @ViewChild(HeroSectionComponent) heroSection!:HeroSectionComponent;
  @ViewChild(AboutUsComponent) aboutSection!:AboutUsComponent;
  constructor(private navbarService:NavBarService){
    this.navbarService.scrollToSection$.subscribe(section => {
    this.scrollToSection(section);
  });
  }
  
  activeSection: string = '';
  isHomePage: boolean = true;

  scrollToServices() {
    this.servicesComponent.scrollTo();
  }
  scrollToTeam() {
    this.TeamComponent.scrollTo();
  }
  scrollToContact(){
    this.contactUsComponent.scrollTo();
  }
  scrollToHero(){
    this.heroSection.scrollTo();
  }
  scrollToAbout(){
    this.aboutSection.scrollTo();
  }
  scrollToSection(section:string){
   switch(section){
  case "hero":
    this.scrollToHero();
    break;
  case "about":
    this.scrollToAbout();
    break;
  case "contact":
    this.scrollToContact();
    break;
  case "services":
    this.scrollToServices();
    break;
  case "team":
    this.scrollToTeam();
    break;
}
    
  }
}
