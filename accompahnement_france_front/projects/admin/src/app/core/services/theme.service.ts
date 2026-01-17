import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';


export type Theme = 'light' | 'dark';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private readonly STORAGE_KEY = 'theme';
  private themeSubject = new BehaviorSubject<'light' | 'dark'>(
    (localStorage.getItem(this.STORAGE_KEY) as 'light' | 'dark') ?? 'light'
  );

  constructor(){
    this.loadTheme();
  }
  theme$ = this.themeSubject.asObservable();
  toggleTheme(): void {
    const newTheme: Theme = this.isDark() ? 'light' : 'dark';
    this.setTheme(newTheme);
  }

  setTheme(theme: Theme): void {
    this.themeSubject.next(theme);
    localStorage.setItem(this.STORAGE_KEY, theme);
  }

  isDark(): boolean {
    return localStorage.getItem(this.STORAGE_KEY) === 'dark';
  }
 get current(): 'light' | 'dark' {
    return this.themeSubject.value;
  }
  private loadTheme(): void {
    const savedTheme = localStorage.getItem(this.STORAGE_KEY) as Theme | null;

    if (savedTheme) {
      this.setTheme(savedTheme);
    } else {
      this.setSystemTheme();
    }
  }

  private setSystemTheme(): void {
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    this.setTheme(prefersDark ? 'dark' : 'light');
  }
}
