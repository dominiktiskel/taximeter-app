jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';

import { TaximeterTimeRangeItemDeleteDialogComponent } from './taximeter-time-range-item-delete-dialog.component';

describe('Component Tests', () => {
  describe('TaximeterTimeRangeItem Management Delete Component', () => {
    let comp: TaximeterTimeRangeItemDeleteDialogComponent;
    let fixture: ComponentFixture<TaximeterTimeRangeItemDeleteDialogComponent>;
    let service: TaximeterTimeRangeItemService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterTimeRangeItemDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(TaximeterTimeRangeItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterTimeRangeItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterTimeRangeItemService);
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
