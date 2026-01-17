import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div [class]="'rounded-lg border bg-card text-card-foreground shadow-sm h-full ' + specialstyles">
      <ng-content></ng-content>
    </div>
  `,
  host: {
    '[class]': 'hostClasses'
  }
})
export class CardComponent {
  @Input() specialstyles: string = '';
  get hostClasses(): string {
    // Extract grid-related classes to apply to host element
      const gridClasses = this.specialstyles
      .split(' ')
      .filter(cls => 
        cls.includes('col-span') || 
        cls.includes('row-span') ||
        cls.includes('col-start') ||
        cls.includes('col-end')
      )
      .join(' ');
    return gridClasses;
  }
}

@Component({
  selector: 'app-card-header',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div [class]="'flex flex-col space-y-1.5 p-6 ' + specialstyles">
      <ng-content></ng-content>
    </div>
  `
})
export class CardHeaderComponent {
  @Input() specialstyles: string = '';
}

@Component({
  selector: 'app-card-title',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h3 [class]="'text-2xl font-semibold leading-none tracking-tight ' + specialstyles">
      <ng-content></ng-content>
    </h3>
  `
})
export class CardTitleComponent {
  @Input() specialstyles: string = '';
}

@Component({
  selector: 'app-card-content',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div [class]="'p-6 pt-0 ' + specialstyles">
      <ng-content></ng-content>
    </div>
  `
})
export class CardContentComponent {
  @Input() specialstyles: string = '';
}

