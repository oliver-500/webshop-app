import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseProductContainerComponent } from './purchase-product-container.component';

describe('PurchaseProductContainerComponent', () => {
  let component: PurchaseProductContainerComponent;
  let fixture: ComponentFixture<PurchaseProductContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchaseProductContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PurchaseProductContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
