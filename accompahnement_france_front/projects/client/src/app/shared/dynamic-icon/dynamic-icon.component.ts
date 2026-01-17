import { Component, Input } from '@angular/core';
import { icons, LucideAngularModule, LucideIconData } from "lucide-angular";
import { UserCheck, RefreshCw, Flag, Home, Users, AlertCircle, FileText, Briefcase } from 'lucide-angular';
@Component({
  selector: 'app-dynamic-icon',
  imports: [LucideAngularModule],
  templateUrl: './dynamic-icon.component.html',
  styleUrl: './dynamic-icon.component.scss'
})
export class DynamicIconComponent {
  @Input() iconName: string = 'circle';
  @Input() size: number = 24;
  @Input() color: string = 'currentColor';
  @Input() strokeWidth: number = 2;
   readonly RefreshCw = RefreshCw;
  readonly UserCheck = UserCheck;
  readonly Flag = Flag;
  readonly Users = Users;
  readonly Home = Home;
  readonly AlertCircle = AlertCircle;
  readonly FileText = FileText;
  readonly Briefcase = Briefcase;
  protected readonly iconsMap = icons as Record<string, LucideIconData>;;

  getIcon(): LucideIconData {
    const iconKey = this.iconName.charAt(0).toUpperCase() + this.iconName.slice(1);
    return this.iconsMap[iconKey] || this.iconsMap['Circle'];
  }
}
