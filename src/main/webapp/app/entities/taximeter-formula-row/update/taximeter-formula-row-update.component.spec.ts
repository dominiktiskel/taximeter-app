jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterFormulaRowService } from '../service/taximeter-formula-row.service';
import { ITaximeterFormulaRow, TaximeterFormulaRow } from '../taximeter-formula-row.model';
import { ITaximeterFormula } from 'app/entities/taximeter-formula/taximeter-formula.model';
import { TaximeterFormulaService } from 'app/entities/taximeter-formula/service/taximeter-formula.service';

import { TaximeterFormulaRowUpdateComponent } from './taximeter-formula-row-update.component';

describe('Component Tests', () => {
  describe('TaximeterFormulaRow Management Update Component', () => {
    let comp: TaximeterFormulaRowUpdateComponent;
    let fixture: ComponentFixture<TaximeterFormulaRowUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterFormulaRowService: TaximeterFormulaRowService;
    let taximeterFormulaService: TaximeterFormulaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFormulaRowUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterFormulaRowUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFormulaRowUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterFormulaRowService = TestBed.inject(TaximeterFormulaRowService);
      taximeterFormulaService = TestBed.inject(TaximeterFormulaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TaximeterFormula query and add missing value', () => {
        const taximeterFormulaRow: ITaximeterFormulaRow = { id: 456 };
        const formula: ITaximeterFormula = { id: 77868 };
        taximeterFormulaRow.formula = formula;

        const taximeterFormulaCollection: ITaximeterFormula[] = [{ id: 20191 }];
        spyOn(taximeterFormulaService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterFormulaCollection })));
        const additionalTaximeterFormulas = [formula];
        const expectedCollection: ITaximeterFormula[] = [...additionalTaximeterFormulas, ...taximeterFormulaCollection];
        spyOn(taximeterFormulaService, 'addTaximeterFormulaToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterFormulaRow });
        comp.ngOnInit();

        expect(taximeterFormulaService.query).toHaveBeenCalled();
        expect(taximeterFormulaService.addTaximeterFormulaToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterFormulaCollection,
          ...additionalTaximeterFormulas
        );
        expect(comp.taximeterFormulasSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taximeterFormulaRow: ITaximeterFormulaRow = { id: 456 };
        const formula: ITaximeterFormula = { id: 3206 };
        taximeterFormulaRow.formula = formula;

        activatedRoute.data = of({ taximeterFormulaRow });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterFormulaRow));
        expect(comp.taximeterFormulasSharedCollection).toContain(formula);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFormulaRow = { id: 123 };
        spyOn(taximeterFormulaRowService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFormulaRow });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFormulaRow }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterFormulaRowService.update).toHaveBeenCalledWith(taximeterFormulaRow);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFormulaRow = new TaximeterFormulaRow();
        spyOn(taximeterFormulaRowService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFormulaRow });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFormulaRow }));
        saveSubject.complete();

        // THEN
        expect(taximeterFormulaRowService.create).toHaveBeenCalledWith(taximeterFormulaRow);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFormulaRow = { id: 123 };
        spyOn(taximeterFormulaRowService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFormulaRow });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterFormulaRowService.update).toHaveBeenCalledWith(taximeterFormulaRow);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTaximeterFormulaById', () => {
        it('Should return tracked TaximeterFormula primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaximeterFormulaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
