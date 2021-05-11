import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterFormulaDetailComponent } from './taximeter-formula-detail.component';

describe('Component Tests', () => {
  describe('TaximeterFormula Management Detail Component', () => {
    let comp: TaximeterFormulaDetailComponent;
    let fixture: ComponentFixture<TaximeterFormulaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterFormulaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterFormula: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterFormulaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterFormulaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterFormula on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterFormula).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
