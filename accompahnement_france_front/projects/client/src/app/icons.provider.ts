import { importProvidersFrom } from '@angular/core';
import { LucideAngularModule, 
  UserCheck, RefreshCw, Flag, Home, Users, AlertCircle, FileText, Briefcase 
  // Add ALL icons you might use from your database
} from 'lucide-angular';

export function provideLucideIconsConfig() {
  return importProvidersFrom(
    LucideAngularModule.pick({ 
     UserCheck, RefreshCw, Flag, Home, Users, AlertCircle, FileText, Briefcase 
      // Add all your icons here
    })
  );
}