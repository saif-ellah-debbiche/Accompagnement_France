export interface Demand {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  wantedServiceLabel: string;
  status: 'CLOSED' | 'IN_PROGRESS' | 'CONTACTED'|'NEW';
  message?: string;
  createdAt:string
}