import { Component, ViewChild } from '@angular/core';
import { ContactUsComponent } from '../../components/contact-us/contact-us.component';
import { ServicesComponent } from '../../components/services/services.component';
import { HeroSectionComponent } from "../../components/hero-section/hero-section.component";
import { AboutUsComponent } from "../../components/about-us/about-us.component";
import { FooterComponent } from "../../../shared/footer/footer.component";
import { TeamComponent } from "../../components/team/team.component";

@Component({
  selector: 'app-home',
  imports: [HeroSectionComponent, ContactUsComponent, AboutUsComponent, ServicesComponent, FooterComponent, TeamComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  @ViewChild(ContactUsComponent) contactUsComponent!: ContactUsComponent;
  @ViewChild(ServicesComponent) servicesComponent!:ServicesComponent;
  @ViewChild(TeamComponent) TeamComponent!:TeamComponent;
  scrollToContact() {
    this.contactUsComponent.scrollTo();
  }
  scrollToServices() {
    this.servicesComponent.scrollTo();
  }
  scrollToTeam() {
    this.TeamComponent.scrollTo();
  }
}
