jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';
import { ITaximeterFixedList, TaximeterFixedList } from '../taximeter-fixed-list.model';

import { TaximeterFixedListUpdateComponent } from './taximeter-fixed-list-update.component';

describe('Component Tests', () => {
  describe('TaximeterFixedList Management Update Component', () => {
    let comp: TaximeterFixedListUpdateComponent;
    let fixture: ComponentFixture<TaximeterFixedListUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterFixedListService: TaximeterFixedListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFixedListUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterFixedListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFixedListUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterFixedListService = TestBed.inject(TaximeterFixedListService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const taximeterFixedList: ITaximeterFixedList = { id: 456 };

        activatedRoute.data = of({ taximeterFixedList });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterFixedList));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFixedList = { id: 123 };
        spyOn(taximeterFixedListService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFixedList });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFixedList }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterFixedListService.update).toHaveBeenCalledWith(taximeterFixedList);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFixedList = new TaximeterFixedList();
        spyOn(taximeterFixedListService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFixedList });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterFixedList }));
        saveSubject.complete();

        // THEN
        expect(taximeterFixedListService.create).toHaveBeenCalledWith(taximeterFixedList);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterFixedList = { id: 123 };
        spyOn(taximeterFixedListService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterFixedList });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterFixedListService.update).toHaveBeenCalledWith(taximeterFixedList);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
