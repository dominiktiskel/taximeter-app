import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';

import { TaximeterTimeRangeItemComponent } from './taximeter-time-range-item.component';

describe('Component Tests', () => {
  describe('TaximeterTimeRangeItem Management Component', () => {
    let comp: TaximeterTimeRangeItemComponent;
    let fixture: ComponentFixture<TaximeterTimeRangeItemComponent>;
    let service: TaximeterTimeRangeItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterTimeRangeItemComponent],
      })
        .overrideTemplate(TaximeterTimeRangeItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterTimeRangeItemComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterTimeRangeItemService);

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
      expect(comp.taximeterTimeRangeItems?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
