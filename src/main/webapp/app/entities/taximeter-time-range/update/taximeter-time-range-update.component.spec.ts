jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterTimeRangeService } from '../service/taximeter-time-range.service';
import { ITaximeterTimeRange, TaximeterTimeRange } from '../taximeter-time-range.model';

import { TaximeterTimeRangeUpdateComponent } from './taximeter-time-range-update.component';

describe('Component Tests', () => {
  describe('TaximeterTimeRange Management Update Component', () => {
    let comp: TaximeterTimeRangeUpdateComponent;
    let fixture: ComponentFixture<TaximeterTimeRangeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterTimeRangeService: TaximeterTimeRangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterTimeRangeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterTimeRangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterTimeRangeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterTimeRangeService = TestBed.inject(TaximeterTimeRangeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const taximeterTimeRange: ITaximeterTimeRange = { id: 456 };

        activatedRoute.data = of({ taximeterTimeRange });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterTimeRange));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterTimeRange = { id: 123 };
        spyOn(taximeterTimeRangeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterTimeRange });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterTimeRange }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterTimeRangeService.update).toHaveBeenCalledWith(taximeterTimeRange);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterTimeRange = new TaximeterTimeRange();
        spyOn(taximeterTimeRangeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterTimeRange });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterTimeRange }));
        saveSubject.complete();

        // THEN
        expect(taximeterTimeRangeService.create).toHaveBeenCalledWith(taximeterTimeRange);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterTimeRange = { id: 123 };
        spyOn(taximeterTimeRangeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterTimeRange });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterTimeRangeService.update).toHaveBeenCalledWith(taximeterTimeRange);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
