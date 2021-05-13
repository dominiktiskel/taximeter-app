jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterFixedListItemService } from '../service/taximeter-fixed-list-item.service';
import { ITaximeterFixedListItem, TaximeterFixedListItem } from '../taximeter-fixed-list-item.model';
import { ITaximeterFixedList } from 'app/entities/taximeter-fixed-list/taximeter-fixed-list.model';
import { TaximeterFixedListService } from 'app/entities/taximeter-fixed-list/service/taximeter-fixed-list.service';

import { TaximeterFixedListItemUpdateComponent } from './taximeter-fixed-list-item-update.component';

describe('Component Tests', () => {
  describe('TaximeterFixedListItem Management Update Component', () => {
    let comp: TaximeterFixedListItemUpdateComponent;
    let fixture: ComponentFixture<TaximeterFixedListItemUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterFixedListItemService: TaximeterFixedListItemService;
    let taximeterFixedListService: TaximeterFixedListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFixedListItemUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterFixedListItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFixedListItemUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterFixedListItemService = TestBed.inject(TaximeterFixedListItemService);
      taximeterFixedListService = TestBed.inject(TaximeterFixedListService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TaximeterFixedList query and add missing value', () => {
        const taximeterFixedListItem: ITaximeterFixedListItem = { id: 456 };
        const list: ITaximeterFixedList = { id: 23038 };
        taximeterFixedListItem.list = list;

        const taximeterFixedListCollection: ITaximeterFixedList[] = [{ id: 76420 }];
        spyOn(taximeterFixedListService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterFixedListCollection })));
        const additionalTaximeterFixedLists = [list];
        const expectedCollection: ITaximeterFixedList[] = [...additionalTaximeterFixedLists, ...taximeterFixedListCollection];
        spyOn(taximeterFixedListService, 'addTaximeterFixedListToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterFixedListItem });
        comp.ngOnInit();

        expect(taximeterFixedListService.query).toHaveBeenCalled();
        expect(taximeterFixedListService.addTaximeterFixedListToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterFixedListCollection,
          ...additionalTaximeterFixedLists
        );
        expect(comp.taximeterFixedListsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taximeterFixedListItem: ITaximeterFixedListItem = { id: 456 };
        const list: ITaximeterFixedList = { id: 44731 };
        taximeterFixedListItem.list = list;

        activatedRoute.data = of({ taximeterFixedListItem });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterFixedListItem));
        expect(comp.taximeterFixedListsSharedCollection).toContain(list);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFixedListItem = { id: 123 };
        spyOn(taximeterFixedListItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFixedListItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFixedListItem }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterFixedListItemService.update).toHaveBeenCalledWith(taximeterFixedListItem);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFixedListItem = new TaximeterFixedListItem();
        spyOn(taximeterFixedListItemService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFixedListItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFixedListItem }));
        saveSubject.complete();

        // THEN
        expect(taximeterFixedListItemService.create).toHaveBeenCalledWith(taximeterFixedListItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFixedListItem = { id: 123 };
        spyOn(taximeterFixedListItemService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFixedListItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterFixedListItemService.update).toHaveBeenCalledWith(taximeterFixedListItem);
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
    });
  });
});
