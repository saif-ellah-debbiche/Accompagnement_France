import { Demand } from "./demand.module";
import { Service } from "./service.module";

export interface DemandStatics{
    fromDays:number;
    percentageChangeLastDays:number;
    totalDemands:number;
    demandsInWaitingStatus:number;
    demandsInProgressing:number;
    closedDemands:number;
    popularServices:Service[];
    recentActivities:Demand[];
}