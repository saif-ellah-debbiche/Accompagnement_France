import { Component, HostBinding } from '@angular/core';
import { RouterModule } from "@angular/router";
import { ToastComponent } from "./shared/toast/toast.component";
import { ThemeService } from './core/services/theme.service';

@Component({
  selector: 'app-root',
  imports: [RouterModule, ToastComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
   @HostBinding('attr.data-theme')
  theme: 'light' | 'dark' = 'light';

  constructor(private themeService: ThemeService) {
    this.themeService.theme$.subscribe(t => this.theme = t);
  }
}
