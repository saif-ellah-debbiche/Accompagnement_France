import { Component, ViewChild } from '@angular/core';
import { RouterModule } from "@angular/router";
import { ContactUsComponent } from '../components/contact-us/contact-us.component';
import { TeamComponent } from '../components/team/team.component';
import { HeaderComponent } from "../shared/header/header.component";
import { FooterComponent } from "../shared/footer/footer.component";

@Component({
  selector: 'app-layout',
  imports: [RouterModule, HeaderComponent, FooterComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
  
}
