import { ErrorHandler, Injectable } from "@angular/core";

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
  
  // Inject your Toast/Notification service here (using Injector if needed to avoid circular dependency)
  
  handleError(error: any): void {
    console.error('--- ANGULAR RUNTIME CRASH (Synchronous) ---', error); 

    let message: string = 'Application Error.';

    // Specifically check for parsing failure (which bypasses interceptors)
    if (error instanceof SyntaxError) {
        message = 'Corrupted Data Error: Failed to read server response.';
        // Notify the user via a Toast/Notification service
        // this.notificationService.showError('Critical Error', message);
    } 
    // This catches errors that occur before or during the response handling pipeline
    
    // Always call the console error for debugging
    console.error(error);
  }
}