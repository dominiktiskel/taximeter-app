import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterFormulaRowDetailComponent } from './taximeter-formula-row-detail.component';

describe('Component Tests', () => {
  describe('TaximeterFormulaRow Management Detail Component', () => {
    let comp: TaximeterFormulaRowDetailComponent;
    let fixture: ComponentFixture<TaximeterFormulaRowDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterFormulaRowDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterFormulaRow: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterFormulaRowDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterFormulaRowDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterFormulaRow on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterFormulaRow).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
