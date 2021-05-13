jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PreferenceService } from '../service/preference.service';
import { IPreference, Preference } from '../preference.model';

import { PreferenceUpdateComponent } from './preference-update.component';

describe('Component Tests', () => {
  describe('Preference Management Update Component', () => {
    let comp: PreferenceUpdateComponent;
    let fixture: ComponentFixture<PreferenceUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let preferenceService: PreferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PreferenceUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PreferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PreferenceUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      preferenceService = TestBed.inject(PreferenceService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const preference: IPreference = { id: 456 };

        activatedRoute.data = of({ preference });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(preference));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const preference = { id: 123 };
        spyOn(preferenceService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ preference });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: preference }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(preferenceService.update).toHaveBeenCalledWith(preference);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const preference = new Preference();
        spyOn(preferenceService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ preference });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: preference }));
        saveSubject.complete();

        // THEN
        expect(preferenceService.create).toHaveBeenCalledWith(preference);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const preference = { id: 123 };
        spyOn(preferenceService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ preference });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(preferenceService.update).toHaveBeenCalledWith(preference);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
