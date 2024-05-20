import { ComponentFixture, TestBed } from '@angular/core/testing';

import GuestpanelComponent from './guestpanel.component';

describe('GuestpanelComponent', () => {
  let component: GuestpanelComponent;
  let fixture: ComponentFixture<GuestpanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuestpanelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GuestpanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
