jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';

import { TaximeterOfferGroupDeleteDialogComponent } from './taximeter-offer-group-delete-dialog.component';

describe('Component Tests', () => {
  describe('TaximeterOfferGroup Management Delete Component', () => {
    let comp: TaximeterOfferGroupDeleteDialogComponent;
    let fixture: ComponentFixture<TaximeterOfferGroupDeleteDialogComponent>;
    let service: TaximeterOfferGroupService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterOfferGroupDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(TaximeterOfferGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterOfferGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterOfferGroupService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
