import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterTimeRangeItemDetailComponent } from './taximeter-time-range-item-detail.component';

describe('Component Tests', () => {
  describe('TaximeterTimeRangeItem Management Detail Component', () => {
    let comp: TaximeterTimeRangeItemDetailComponent;
    let fixture: ComponentFixture<TaximeterTimeRangeItemDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterTimeRangeItemDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterTimeRangeItem: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterTimeRangeItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterTimeRangeItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterTimeRangeItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterTimeRangeItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
