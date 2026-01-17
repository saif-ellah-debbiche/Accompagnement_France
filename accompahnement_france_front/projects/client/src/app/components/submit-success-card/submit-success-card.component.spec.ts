import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitSuccessCardComponent } from './submit-success-card.component';

describe('SubmitSuccessCardComponent', () => {
  let component: SubmitSuccessCardComponent;
  let fixture: ComponentFixture<SubmitSuccessCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubmitSuccessCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmitSuccessCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
