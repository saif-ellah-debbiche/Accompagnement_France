export interface DemandRequest {
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  message: string;
  wantedServiceLabel:string;
  source:    'WEBSITE'|'FACEBOOK_AD'|'GOOGLE_AD'|'WHATSAPP';
}