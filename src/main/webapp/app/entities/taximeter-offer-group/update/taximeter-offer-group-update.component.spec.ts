jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';
import { ITaximeterOfferGroup, TaximeterOfferGroup } from '../taximeter-offer-group.model';
import { ITaximeterOffer } from 'app/entities/taximeter-offer/taximeter-offer.model';
import { TaximeterOfferService } from 'app/entities/taximeter-offer/service/taximeter-offer.service';

import { TaximeterOfferGroupUpdateComponent } from './taximeter-offer-group-update.component';

describe('Component Tests', () => {
  describe('TaximeterOfferGroup Management Update Component', () => {
    let comp: TaximeterOfferGroupUpdateComponent;
    let fixture: ComponentFixture<TaximeterOfferGroupUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taximeterOfferGroupService: TaximeterOfferGroupService;
    let taximeterOfferService: TaximeterOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterOfferGroupUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaximeterOfferGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterOfferGroupUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taximeterOfferGroupService = TestBed.inject(TaximeterOfferGroupService);
      taximeterOfferService = TestBed.inject(TaximeterOfferService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TaximeterOffer query and add missing value', () => {
        const taximeterOfferGroup: ITaximeterOfferGroup = { id: 456 };
        const offer: ITaximeterOffer = { id: 51956 };
        taximeterOfferGroup.offer = offer;

        const taximeterOfferCollection: ITaximeterOffer[] = [{ id: 78555 }];
        spyOn(taximeterOfferService, 'query').and.returnValue(of(new HttpResponse({ body: taximeterOfferCollection })));
        const additionalTaximeterOffers = [offer];
        const expectedCollection: ITaximeterOffer[] = [...additionalTaximeterOffers, ...taximeterOfferCollection];
        spyOn(taximeterOfferService, 'addTaximeterOfferToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taximeterOfferGroup });
        comp.ngOnInit();

        expect(taximeterOfferService.query).toHaveBeenCalled();
        expect(taximeterOfferService.addTaximeterOfferToCollectionIfMissing).toHaveBeenCalledWith(
          taximeterOfferCollection,
          ...additionalTaximeterOffers
        );
        expect(comp.taximeterOffersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taximeterOfferGroup: ITaximeterOfferGroup = { id: 456 };
        const offer: ITaximeterOffer = { id: 29496 };
        taximeterOfferGroup.offer = offer;

        activatedRoute.data = of({ taximeterOfferGroup });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taximeterOfferGroup));
        expect(comp.taximeterOffersSharedCollection).toContain(offer);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOfferGroup = { id: 123 };
        spyOn(taximeterOfferGroupService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOfferGroup });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterOfferGroup }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taximeterOfferGroupService.update).toHaveBeenCalledWith(taximeterOfferGroup);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOfferGroup = new TaximeterOfferGroup();
        spyOn(taximeterOfferGroupService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOfferGroup });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taximeterOfferGroup }));
        saveSubject.complete();

        // THEN
        expect(taximeterOfferGroupService.create).toHaveBeenCalledWith(taximeterOfferGroup);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taximeterOfferGroup = { id: 123 };
        spyOn(taximeterOfferGroupService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taximeterOfferGroup });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taximeterOfferGroupService.update).toHaveBeenCalledWith(taximeterOfferGroup);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTaximeterOfferById', () => {
        it('Should return tracked TaximeterOffer primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaximeterOfferById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
