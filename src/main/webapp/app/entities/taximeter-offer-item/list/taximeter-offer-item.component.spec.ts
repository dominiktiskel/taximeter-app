import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterOfferItemService } from '../service/taximeter-offer-item.service';

import { TaximeterOfferItemComponent } from './taximeter-offer-item.component';

describe('Component Tests', () => {
  describe('TaximeterOfferItem Management Component', () => {
    let comp: TaximeterOfferItemComponent;
    let fixture: ComponentFixture<TaximeterOfferItemComponent>;
    let service: TaximeterOfferItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterOfferItemComponent],
      })
        .overrideTemplate(TaximeterOfferItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterOfferItemComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterOfferItemService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taximeterOfferItems?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
