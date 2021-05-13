import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterOfferDetailComponent } from './taximeter-offer-detail.component';

describe('Component Tests', () => {
  describe('TaximeterOffer Management Detail Component', () => {
    let comp: TaximeterOfferDetailComponent;
    let fixture: ComponentFixture<TaximeterOfferDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterOfferDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterOffer: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterOfferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterOfferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterOffer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterOffer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
