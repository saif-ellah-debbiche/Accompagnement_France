import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Toast } from '../models/toast.module';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
private toastSubject = new Subject<Toast>();
  toast$ = this.toastSubject.asObservable();
  constructor() { }

  success(title:string,message: string) {
  this.toastSubject.next({title, message, type: 'success' });
  }

  error(title:string,message: string) {
    this.toastSubject.next({title, message, type: 'error' });
  }
}
