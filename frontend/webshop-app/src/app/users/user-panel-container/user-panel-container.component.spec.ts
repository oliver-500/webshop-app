import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPanelContainerComponent } from './user-panel-container.component';

describe('UserPanelContainerComponent', () => {
  let component: UserPanelContainerComponent;
  let fixture: ComponentFixture<UserPanelContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPanelContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPanelContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
