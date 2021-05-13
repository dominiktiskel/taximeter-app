import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';

import { TaximeterFixedListComponent } from './taximeter-fixed-list.component';

describe('Component Tests', () => {
  describe('TaximeterFixedList Management Component', () => {
    let comp: TaximeterFixedListComponent;
    let fixture: ComponentFixture<TaximeterFixedListComponent>;
    let service: TaximeterFixedListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFixedListComponent],
      })
        .overrideTemplate(TaximeterFixedListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFixedListComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterFixedListService);

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
      expect(comp.taximeterFixedLists?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
