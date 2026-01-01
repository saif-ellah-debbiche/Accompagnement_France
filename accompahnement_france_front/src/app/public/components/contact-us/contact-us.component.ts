import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LucideAngularModule, Mail, MapPin, Phone } from 'lucide-angular';
import { SITE_CONFIG } from '../../../core/config/site.config';
import { ContactForm } from '../../../core/models/contactForm.moldel';

@Component({
  selector: 'app-contact-us',
  imports: [CommonModule, FormsModule,ReactiveFormsModule, LucideAngularModule],
  templateUrl: './contact-us.component.html',
  styleUrl: './contact-us.component.scss'
})
export class ContactUsComponent {
 @ViewChild('contactSection') contactSectionRef!: ElementRef;
  services = SITE_CONFIG.services;
  owner = SITE_CONFIG.owner;

  contactForm: FormGroup;

  constructor(private fb: FormBuilder){
    this.contactForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required,Validators.pattern(/^\+?\d{8,15}$/) // allows + at start and 8-15 digits
]],
      wantedService: ['', [Validators.required]],
      description: ''
    });
  }

  scrollTo() {
    this.contactSectionRef.nativeElement.scrollIntoView({ behavior: 'smooth' });
  }



  readonly MailIcon = Mail;
  readonly PhoneIcon = Phone;
  readonly MapPinIcon = MapPin;




  
  // Form data
  formData: ContactForm = {
    name: '',
    email: '',
    phone: '',
    wantedService:'',
    description: ''

  };

  // Handle form submission
  onSubmit(event: Event): void {
    event.preventDefault();
 if (this.contactForm.invalid) {
      // Mark all fields as touched so errors show
      this.contactForm.markAllAsTouched();
      return;
    }
    console.log('Form submitted:', this.formData);
    
    // TODO: Add your form submission logic here
    // Example: this.contactService.sendMessage(this.formData).subscribe(...)
    
    // Reset form after submission
    this.formData = {
      name: '',
      email: '',
      phone: '',
      description: '',
      wantedService:''
    };
  }
}
