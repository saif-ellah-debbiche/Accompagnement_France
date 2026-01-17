import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LucideAngularModule, Mail, MapPin, Phone } from 'lucide-angular';
import { SITE_CONFIG } from '../../core/config/site.config';
import { ClientService } from '../../core/services/client.service';
import { DemandRequest } from '../../core/models/contactForm.moldel';
import { Service } from '../../core/models/service.model';
import { SubmitSuccessCardComponent } from "../submit-success-card/submit-success-card.component";
@Component({
  selector: 'app-contact-us',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, LucideAngularModule, SubmitSuccessCardComponent],
  templateUrl: './contact-us.component.html',
  styleUrl: './contact-us.component.scss'
})
export class ContactUsComponent {
 @ViewChild('contactSection') contactSectionRef!: ElementRef;
  services:Service[] =[] ;
  owner = SITE_CONFIG.owner;
  isSubmitting=false;
  submissionSucceeded =false;
  errorMessage:string|null=null;
  contactForm: FormGroup;

  constructor(private fb: FormBuilder,private clientService:ClientService){
    this.clientService.getOfferedServices().subscribe({
      next:response=>{
        this.services=response;
      }
    })

    this.contactForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required,Validators.pattern(/^\+?\d{8,15}$/) // allows + at start and 8-15 digits
]],
      wantedService: ['', [Validators.required]],
      description: [''],
      website:['']
    });
  }

  scrollTo() {
    this.contactSectionRef.nativeElement.scrollIntoView({ behavior: 'smooth' });
  }



  readonly MailIcon = Mail;
  readonly PhoneIcon = Phone;
  readonly MapPinIcon = MapPin;


 bachHome(){
      this.submissionSucceeded=false;
    }

  // Handle form submission
  onSubmit(event: Event): void {
    event.preventDefault();
    this.errorMessage=null;
 if (this.contactForm.invalid) {
      // Mark all fields as touched so errors show
      this.contactForm.markAllAsTouched();
      return;
    }
    this.isSubmitting=true;

    const newDemand: DemandRequest= {
      email:this.contactForm.get("email")?.value,
      firstName:this.contactForm.get("firstName")?.value,
      lastName:this.contactForm.get("lastName")?.value,
      message:this.contactForm.get("description")?.value,
      phoneNumber:this.contactForm.get("phone")?.value,
      wantedServiceLabel:this.contactForm.get("wantedService")?.value,
      source:this.contactForm.get("website")?.value==''?null:this.contactForm.get("website")?.value,
    }

   
    this.clientService.createDemand(newDemand).subscribe({
      next:response=>{
        this.isSubmitting=false;
        this.submissionSucceeded =true;
        this.contactForm.reset();
        console.log("succeed")
      },
      error:err=>{
        this.isSubmitting=false;
        this.errorMessage=err.error.message
        console.log(err)
      }
    })
   
  }
}
