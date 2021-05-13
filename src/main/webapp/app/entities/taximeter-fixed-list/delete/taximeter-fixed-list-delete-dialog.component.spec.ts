jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';

import { TaximeterFixedListDeleteDialogComponent } from './taximeter-fixed-list-delete-dialog.component';

describe('Component Tests', () => {
  describe('TaximeterFixedList Management Delete Component', () => {
    let comp: TaximeterFixedListDeleteDialogComponent;
    let fixture: ComponentFixture<TaximeterFixedListDeleteDialogComponent>;
    let service: TaximeterFixedListService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFixedListDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(TaximeterFixedListDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterFixedListDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterFixedListService);
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
