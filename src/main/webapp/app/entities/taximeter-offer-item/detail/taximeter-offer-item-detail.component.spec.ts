import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterOfferItemDetailComponent } from './taximeter-offer-item-detail.component';

describe('Component Tests', () => {
  describe('TaximeterOfferItem Management Detail Component', () => {
    let comp: TaximeterOfferItemDetailComponent;
    let fixture: ComponentFixture<TaximeterOfferItemDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterOfferItemDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterOfferItem: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterOfferItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterOfferItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterOfferItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterOfferItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
