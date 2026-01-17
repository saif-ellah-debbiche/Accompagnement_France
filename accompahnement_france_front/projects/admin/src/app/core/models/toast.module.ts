export interface Toast {
  message: string;
  title:string;
  type: 'success' | 'error' | 'info' | 'warning';
}