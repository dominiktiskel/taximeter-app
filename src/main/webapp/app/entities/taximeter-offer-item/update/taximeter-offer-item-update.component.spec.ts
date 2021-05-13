jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterOfferItemService } from '../service/taximeter-offer-item.service';
import { ITaximeterOfferItem, TaximeterOfferItem } from '../taximeter-offer-item.model';
import { ITaximeterFixedList } from 'app/entities/taximeter-fixed-list/taximeter-fixed-list.model';
import { TaximeterFixedListService } from 'app/entities/taximeter-fixed-list/service/taximeter-fixed-list.service';
import { ITaximeterFormula } from 'app/entities/taximeter-formula/taximeter-formula.model';
import { TaximeterFormulaService } from 'app/entities/taximeter-formula/service/taximeter-formula.service';
import { ITaximeterTimeRange } from 'app/entities/taximeter-time-range/taximeter-time-range.model';
import { TaximeterTimeRangeService } from 'app/entities/taximeter-time-range/service/taximeter-time-range.service';
import { IPreference } from 'app/entities/preference/preference.model';
import { PreferenceService } from 'app/entities/preference/service/preference.service';
import { ITaximeterOfferGroup } from 'app/entities/taximeter-offer-group/taximeter-offer-group.model';
import { TaximeterOfferGroupService } from 'app/entities/taximeter-offer-group/service/taximeter-offer-group.service';

import { TaximeterOfferItemUpdateComponent } from './taximeter-offer-item-update.component';

describe('Component Tests', () => {
  describe('TaximeterOfferItem Management Update Component', () => {
    let comp: TaximeterOfferItemUpdateComponent;
    let fixture: ComponentFixture<TaximeterOfferItemUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterOfferItemService: TaximeterOfferItemService;
    let taximeterFixedListService: TaximeterFixedListService;
    let taximeterFormulaService: TaximeterFormulaService;
    let taximeterTimeRangeService: TaximeterTimeRangeService;
    let preferenceService: PreferenceService;
    let taximeterOfferGroupService: TaximeterOfferGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterOfferItemUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterOfferItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterOfferItemUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterOfferItemService = TestBed.inject(TaximeterOfferItemService);
      taximeterFixedListService = TestBed.inject(TaximeterFixedListService);
      taximeterFormulaService = TestBed.inject(TaximeterFormulaService);
      taximeterTimeRangeService = TestBed.inject(TaximeterTimeRangeService);
      preferenceService = TestBed.inject(PreferenceService);
      taximeterOfferGroupService = TestBed.inject(TaximeterOfferGroupService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TaximeterFixedList query and add missing value', () => {
        const taximeterOfferItem: ITaximeterOfferItem = { id: 456 };
        const fixedList1: ITaximeterFixedList = { id: 78209 };
        taximeterOfferItem.fixedList1 = fixedList1;
        const fixedList2: ITaximeterFixedList = { id: 35900 };
        taximeterOfferItem.fixedList2 = fixedList2;

        const taximeterFixedListCollection: ITaximeterFixedList[] = [{ id: 6112 }];
        spyOn(taximeterFixedListService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterFixedListCollection })));
        const additionalTaximeterFixedLists = [fixedList1, fixedList2];
        const expectedCollection: ITaximeterFixedList[] = [...additionalTaximeterFixedLists, ...taximeterFixedListCollection];
        spyOn(taximeterFixedListService, 'addTaximeterFixedListToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        expect(taximeterFixedListService.query).toHaveBeenCalled();
        expect(taximeterFixedListService.addTaximeterFixedListToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterFixedListCollection,
          ...additionalTaximeterFixedLists
        );
        expect(comp.taximeterFixedListsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call TaximeterFormula query and add missing value', () => {
        const taximeterOfferItem: ITaximeterOfferItem = { id: 456 };
        const formula: ITaximeterFormula = { id: 44503 };
        taximeterOfferItem.formula = formula;

        const taximeterFormulaCollection: ITaximeterFormula[] = [{ id: 34317 }];
        spyOn(taximeterFormulaService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterFormulaCollection })));
        const additionalTaximeterFormulas = [formula];
        const expectedCollection: ITaximeterFormula[] = [...additionalTaximeterFormulas, ...taximeterFormulaCollection];
        spyOn(taximeterFormulaService, 'addTaximeterFormulaToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        expect(taximeterFormulaService.query).toHaveBeenCalled();
        expect(taximeterFormulaService.addTaximeterFormulaToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterFormulaCollection,
          ...additionalTaximeterFormulas
        );
        expect(comp.taximeterFormulasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call TaximeterTimeRange query and add missing value', () => {
        const taximeterOfferItem: ITaximeterOfferItem = { id: 456 };
        const timeRange: ITaximeterTimeRange = { id: 64124 };
        taximeterOfferItem.timeRange = timeRange;

        const taximeterTimeRangeCollection: ITaximeterTimeRange[] = [{ id: 91576 }];
        spyOn(taximeterTimeRangeService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterTimeRangeCollection })));
        const additionalTaximeterTimeRanges = [timeRange];
        const expectedCollection: ITaximeterTimeRange[] = [...additionalTaximeterTimeRanges, ...taximeterTimeRangeCollection];
        spyOn(taximeterTimeRangeService, 'addTaximeterTimeRangeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        expect(taximeterTimeRangeService.query).toHaveBeenCalled();
        expect(taximeterTimeRangeService.addTaximeterTimeRangeToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterTimeRangeCollection,
          ...additionalTaximeterTimeRanges
        );
        expect(comp.taximeterTimeRangesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Preference query and add missing value', () => {
        const taximeterOfferItem: ITaximeterOfferItem = { id: 456 };
        const preferences: IPreference[] = [{ id: 42780 }];
        taximeterOfferItem.preferences = preferences;

        const preferenceCollection: IPreference[] = [{ id: 70407 }];
        spyOn(preferenceService, 'query').and.returnValue(of(new HttpResponse({ body: preferenceCollection })));
        const additionalPreferences = [...preferences];
        const expectedCollection: IPreference[] = [...additionalPreferences, ...preferenceCollection];
        spyOn(preferenceService, 'addPreferenceToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        expect(preferenceService.query).toHaveBeenCalled();
        expect(preferenceService.addPreferenceToCollectionIfMissing).toHaveBeenCalledWith(preferenceCollection, ...additionalPreferences);
        expect(comp.preferencesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call TaximeterOfferGroup query and add missing value', () => {
        const taximeterOfferItem: ITaximeterOfferItem = { id: 456 };
        const group: ITaximeterOfferGroup = { id: 93069 };
        taximeterOfferItem.group = group;

        const taximeterOfferGroupCollection: ITaximeterOfferGroup[] = [{ id: 18577 }];
        spyOn(taximeterOfferGroupService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterOfferGroupCollection })));
        const additionalTaximeterOfferGroups = [group];
        const expectedCollection: ITaximeterOfferGroup[] = [...additionalTaximeterOfferGroups, ...taximeterOfferGroupCollection];
        spyOn(taximeterOfferGroupService, 'addTaximeterOfferGroupToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        expect(taximeterOfferGroupService.query).toHaveBeenCalled();
        expect(taximeterOfferGroupService.addTaximeterOfferGroupToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterOfferGroupCollection,
          ...additionalTaximeterOfferGroups
        );
        expect(comp.taximeterOfferGroupsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taximeterOfferItem: ITaximeterOfferItem = { id: 456 };
        const fixedList1: ITaximeterFixedList = { id: 97026 };
        taximeterOfferItem.fixedList1 = fixedList1;
        const fixedList2: ITaximeterFixedList = { id: 21546 };
        taximeterOfferItem.fixedList2 = fixedList2;
        const formula: ITaximeterFormula = { id: 72463 };
        taximeterOfferItem.formula = formula;
        const timeRange: ITaximeterTimeRange = { id: 3072 };
        taximeterOfferItem.timeRange = timeRange;
        const preferences: IPreference = { id: 30030 };
        taximeterOfferItem.preferences = [preferences];
        const group: ITaximeterOfferGroup = { id: 34057 };
        taximeterOfferItem.group = group;

        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterOfferItem));
        expect(comp.taximeterFixedListsSharedCollection).toContain(fixedList1);
        expect(comp.taximeterFixedListsSharedCollection).toContain(fixedList2);
        expect(comp.taximeterFormulasSharedCollection).toContain(formula);
        expect(comp.taximeterTimeRangesSharedCollection).toContain(timeRange);
        expect(comp.preferencesSharedCollection).toContain(preferences);
        expect(comp.taximeterOfferGroupsSharedCollection).toContain(group);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOfferItem = { id: 123 };
        spyOn(taximeterOfferItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterOfferItem }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterOfferItemService.update).toHaveBeenCalledWith(taximeterOfferItem);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOfferItem = new TaximeterOfferItem();
        spyOn(taximeterOfferItemService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterOfferItem }));
        saveSubject.complete();

        // THEN
        expect(taximeterOfferItemService.create).toHaveBeenCalledWith(taximeterOfferItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOfferItem = { id: 123 };
        spyOn(taximeterOfferItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOfferItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterOfferItemService.update).toHaveBeenCalledWith(taximeterOfferItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTaximeterFixedListById', () => {
        it('Should return tracked TaximeterFixedList primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaximeterFixedListById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTaximeterFormulaById', () => {
        it('Should return tracked TaximeterFormula primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaximeterFormulaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTaximeterTimeRangeById', () => {
        it('Should return tracked TaximeterTimeRange primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaximeterTimeRangeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackPreferenceById', () => {
        it('Should return tracked Preference primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPreferenceById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTaximeterOfferGroupById', () => {
        it('Should return tracked TaximeterOfferGroup primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaximeterOfferGroupById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedPreference', () => {
        it('Should return option if no Preference is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedPreference(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Preference for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedPreference(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Preference is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedPreference(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
