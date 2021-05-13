import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterOfferService } from '../service/taximeter-offer.service';

import { TaximeterOfferComponent } from './taximeter-offer.component';

describe('Component Tests', () => {
  describe('TaximeterOffer Management Component', () => {
    let comp: TaximeterOfferComponent;
    let fixture: ComponentFixture<TaximeterOfferComponent>;
    let service: TaximeterOfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterOfferComponent],
      })
        .overrideTemplate(TaximeterOfferComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterOfferComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterOfferService);

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
      expect(comp.taximeterOffers?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
