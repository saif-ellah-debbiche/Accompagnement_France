import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DemandRequest } from '../models/contactForm.moldel';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Service } from '../models/service.model';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private servicesSubject = new BehaviorSubject<Service[]>([]);
  services$: Observable<Service[]> = this.servicesSubject.asObservable();
  constructor(private http:HttpClient) {
  this.getOfferedServices().pipe(tap(
    services => this.servicesSubject.next(services)
  ))
      .subscribe();
   }

   createDemand(demand:DemandRequest):Observable<String>{
          return this.http.post<String>("/api/demands",demand)
   }
   getOfferedServices():Observable<Service[]>{
      return this.http.get<Service[]>("/api/services");
   }
}
