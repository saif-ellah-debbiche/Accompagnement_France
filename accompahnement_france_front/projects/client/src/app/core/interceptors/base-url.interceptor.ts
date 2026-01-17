import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../../envirements/environment.prod';

export const baseUrlInterceptor: HttpInterceptorFn = (req, next) => {
const isApiRequest = req.url.startsWith('/api/');

  // Check if the URL is relative (i.e., not a full external URL) AND is an API call
  if (!req.url.startsWith('http') && isApiRequest) {
    // Clone the request and prepend the base URL
    const apiReq = req.clone({ url: `${environment.apiUrl}${req.url}` });
    console.log("sending request to " + `${environment.apiUrl}${req.url}`)
    return next(apiReq);
  }

  // For all other requests (like external URLs or assets), pass them through
  return next(req);
};
