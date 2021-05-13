jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterOfferService } from '../service/taximeter-offer.service';
import { ITaximeterOffer, TaximeterOffer } from '../taximeter-offer.model';

import { TaximeterOfferUpdateComponent } from './taximeter-offer-update.component';

describe('Component Tests', () => {
  describe('TaximeterOffer Management Update Component', () => {
    let comp: TaximeterOfferUpdateComponent;
    let fixture: ComponentFixture<TaximeterOfferUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterOfferService: TaximeterOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterOfferUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterOfferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterOfferUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterOfferService = TestBed.inject(TaximeterOfferService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const taximeterOffer: ITaximeterOffer = { id: 456 };

        activatedRoute.data = of({ taximeterOffer });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterOffer));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOffer = { id: 123 };
        spyOn(taximeterOfferService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOffer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterOffer }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterOfferService.update).toHaveBeenCalledWith(taximeterOffer);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOffer = new TaximeterOffer();
        spyOn(taximeterOfferService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOffer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterOffer }));
        saveSubject.complete();

        // THEN
        expect(taximeterOfferService.create).toHaveBeenCalledWith(taximeterOffer);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOffer = { id: 123 };
        spyOn(taximeterOfferService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOffer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterOfferService.update).toHaveBeenCalledWith(taximeterOffer);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
