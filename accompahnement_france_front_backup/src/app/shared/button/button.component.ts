import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  imports: [],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss'
})
export class ButtonComponent {
  @Input() variant: 'default' | 'outline' = 'default';
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() type: 'button' | 'submit' = 'button';

  get classes(): string {
    const variants = {
      default: 'bg-primary text-primary-foreground hover:bg-primary/90',
      outline: 'border border-border bg-transparent hover:bg-[#f0c6ba]'
    };

    const sizes = {
      sm: 'h-9 px-2 text-sm',
      md: 'h-10 px-4 text-base',
      lg: 'h-12 px-6 text-base '
    };

    return `${variants[this.variant]} ${sizes[this.size]} cursor-pointer`;
  }
}
