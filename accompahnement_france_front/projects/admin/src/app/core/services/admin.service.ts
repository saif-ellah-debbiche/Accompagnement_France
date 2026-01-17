import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RetentionParams } from '../models/retention-params.module';
import { Demand } from '../models/demand.module';
import { DemandStatics } from '../models/demand-statics.module';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }


  getRetentionParams():Observable<RetentionParams>{
    return this.http.get<RetentionParams>("/api/admin/retention/active",{
      withCredentials:true
    })
  }
  setRetentionPolicy(retentionPeriod:string,cleanupAction:string):Observable<RetentionParams>{
    const retentionPeriodInt = parseInt(retentionPeriod);
    return this.http.post<RetentionParams>("/api/admin/retention",{
      daysToRetainAfterClosure: retentionPeriodInt,
      actionType :cleanupAction
    },{
      withCredentials:true
    })
  }

  getDemands():Observable<Demand[]>{
       return this.http.get<Demand[]>("/api/demands",{
      withCredentials:true
    })
  }
  changeDemandStatus(demandId:string,demandStatus:'CLOSED' | 'IN_PROGRESS' | 'CONTACTED'|'NEW'){
     return this.http.put<Demand[]>("/api/demands/"+demandId,{
      demandStatus:demandStatus 
     },{
      withCredentials:true
    })
  }

  getDemandStatics():Observable<DemandStatics>{
      return this.http.get<DemandStatics>("/api/demands/statics",{
      withCredentials:true
    })
  }
  forgetPassword(email:string):Observable<boolean>{
      return this.http.post<boolean>("/api/auth/forgot-password",{
        email
      },{
      withCredentials:true
    })
  }
}
