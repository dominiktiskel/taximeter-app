jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterFormulaService } from '../service/taximeter-formula.service';
import { ITaximeterFormula, TaximeterFormula } from '../taximeter-formula.model';

import { TaximeterFormulaUpdateComponent } from './taximeter-formula-update.component';

describe('Component Tests', () => {
  describe('TaximeterFormula Management Update Component', () => {
    let comp: TaximeterFormulaUpdateComponent;
    let fixture: ComponentFixture<TaximeterFormulaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterFormulaService: TaximeterFormulaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFormulaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterFormulaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFormulaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterFormulaService = TestBed.inject(TaximeterFormulaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const taximeterFormula: ITaximeterFormula = { id: 456 };

        activatedRoute.data = of({ taximeterFormula });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterFormula));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFormula = { id: 123 };
        spyOn(taximeterFormulaService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFormula });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFormula }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterFormulaService.update).toHaveBeenCalledWith(taximeterFormula);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFormula = new TaximeterFormula();
        spyOn(taximeterFormulaService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFormula });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFormula }));
        saveSubject.complete();

        // THEN
        expect(taximeterFormulaService.create).toHaveBeenCalledWith(taximeterFormula);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFormula = { id: 123 };
        spyOn(taximeterFormulaService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFormula });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterFormulaService.update).toHaveBeenCalledWith(taximeterFormula);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
