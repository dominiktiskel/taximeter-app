import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterTimeRangeDetailComponent } from './taximeter-time-range-detail.component';

describe('Component Tests', () => {
  describe('TaximeterTimeRange Management Detail Component', () => {
    let comp: TaximeterTimeRangeDetailComponent;
    let fixture: ComponentFixture<TaximeterTimeRangeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterTimeRangeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterTimeRange: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterTimeRangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterTimeRangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterTimeRange on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterTimeRange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
