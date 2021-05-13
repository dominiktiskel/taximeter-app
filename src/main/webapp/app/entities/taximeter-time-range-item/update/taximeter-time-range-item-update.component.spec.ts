jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';
import { ITaximeterTimeRangeItem, TaximeterTimeRangeItem } from '../taximeter-time-range-item.model';
import { ITaximeterTimeRange } from 'app/entities/taximeter-time-range/taximeter-time-range.model';
import { TaximeterTimeRangeService } from 'app/entities/taximeter-time-range/service/taximeter-time-range.service';

import { TaximeterTimeRangeItemUpdateComponent } from './taximeter-time-range-item-update.component';

describe('Component Tests', () => {
  describe('TaximeterTimeRangeItem Management Update Component', () => {
    let comp: TaximeterTimeRangeItemUpdateComponent;
    let fixture: ComponentFixture<TaximeterTimeRangeItemUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterTimeRangeItemService: TaximeterTimeRangeItemService;
    let taximeterTimeRangeService: TaximeterTimeRangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterTimeRangeItemUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterTimeRangeItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterTimeRangeItemUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterTimeRangeItemService = TestBed.inject(TaximeterTimeRangeItemService);
      taximeterTimeRangeService = TestBed.inject(TaximeterTimeRangeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TaximeterTimeRange query and add missing value', () => {
        const taximeterTimeRangeItem: ITaximeterTimeRangeItem = { id: 456 };
        const range: ITaximeterTimeRange = { id: 87633 };
        taximeterTimeRangeItem.range = range;

        const taximeterTimeRangeCollection: ITaximeterTimeRange[] = [{ id: 25207 }];
        spyOn(taximeterTimeRangeService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterTimeRangeCollection })));
        const additionalTaximeterTimeRanges = [range];
        const expectedCollection: ITaximeterTimeRange[] = [...additionalTaximeterTimeRanges, ...taximeterTimeRangeCollection];
        spyOn(taximeterTimeRangeService, 'addTaximeterTimeRangeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterTimeRangeItem });
        comp.ngOnInit();

        expect(taximeterTimeRangeService.query).toHaveBeenCalled();
        expect(taximeterTimeRangeService.addTaximeterTimeRangeToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterTimeRangeCollection,
          ...additionalTaximeterTimeRanges
        );
        expect(comp.taximeterTimeRangesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taximeterTimeRangeItem: ITaximeterTimeRangeItem = { id: 456 };
        const range: ITaximeterTimeRange = { id: 31740 };
        taximeterTimeRangeItem.range = range;

        activatedRoute.data = of({ taximeterTimeRangeItem });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterTimeRangeItem));
        expect(comp.taximeterTimeRangesSharedCollection).toContain(range);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterTimeRangeItem = { id: 123 };
        spyOn(taximeterTimeRangeItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterTimeRangeItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterTimeRangeItem }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterTimeRangeItemService.update).toHaveBeenCalledWith(taximeterTimeRangeItem);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterTimeRangeItem = new TaximeterTimeRangeItem();
        spyOn(taximeterTimeRangeItemService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterTimeRangeItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterTimeRangeItem }));
        saveSubject.complete();

        // THEN
        expect(taximeterTimeRangeItemService.create).toHaveBeenCalledWith(taximeterTimeRangeItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterTimeRangeItem = { id: 123 };
        spyOn(taximeterTimeRangeItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterTimeRangeItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterTimeRangeItemService.update).toHaveBeenCalledWith(taximeterTimeRangeItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTaximeterTimeRangeById', () => {
        it('Should return tracked TaximeterTimeRange primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaximeterTimeRangeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
