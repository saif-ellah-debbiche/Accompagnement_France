import { Component, EventEmitter, Output } from '@angular/core';
import { CardComponent } from "../../../../../admin/src/app/shared/card";

@Component({
  selector: 'app-submit-success-card',
  imports: [CardComponent],
  templateUrl: './submit-success-card.component.html',
  styleUrl: './submit-success-card.component.scss'
})
export class SubmitSuccessCardComponent {
@Output() backToHome = new EventEmitter<void>();
onBackToHome() {
    this.backToHome.emit();
  }
}
