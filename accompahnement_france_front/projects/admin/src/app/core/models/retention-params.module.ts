export interface RetentionParams{
    daysToRetainAfterClosure : number;
    actionType:"DELETE"|"ANONYMIZE";
}