import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NavBarService {
 private scrollToSectionSubject = new Subject<string>();
  scrollToSection$ = this.scrollToSectionSubject.asObservable();

  scrollTo(sectionId: string): void {
    this.scrollToSectionSubject.next(sectionId);
  }
}
