import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';

import { TaximeterOfferGroupComponent } from './taximeter-offer-group.component';

describe('Component Tests', () => {
  describe('TaximeterOfferGroup Management Component', () => {
    let comp: TaximeterOfferGroupComponent;
    let fixture: ComponentFixture<TaximeterOfferGroupComponent>;
    let service: TaximeterOfferGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterOfferGroupComponent],
      })
        .overrideTemplate(TaximeterOfferGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterOfferGroupComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterOfferGroupService);

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
      expect(comp.taximeterOfferGroups?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
