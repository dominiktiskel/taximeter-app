import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterOfferGroupDetailComponent } from './taximeter-offer-group-detail.component';

describe('Component Tests', () => {
  describe('TaximeterOfferGroup Management Detail Component', () => {
    let comp: TaximeterOfferGroupDetailComponent;
    let fixture: ComponentFixture<TaximeterOfferGroupDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterOfferGroupDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterOfferGroup: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterOfferGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterOfferGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterOfferGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterOfferGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
