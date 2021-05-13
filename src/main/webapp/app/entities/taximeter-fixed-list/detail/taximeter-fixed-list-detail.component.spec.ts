import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterFixedListDetailComponent } from './taximeter-fixed-list-detail.component';

describe('Component Tests', () => {
  describe('TaximeterFixedList Management Detail Component', () => {
    let comp: TaximeterFixedListDetailComponent;
    let fixture: ComponentFixture<TaximeterFixedListDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterFixedListDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterFixedList: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterFixedListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterFixedListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterFixedList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterFixedList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
