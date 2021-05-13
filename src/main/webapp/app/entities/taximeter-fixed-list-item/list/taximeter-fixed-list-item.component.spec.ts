import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterFixedListItemService } from '../service/taximeter-fixed-list-item.service';

import { TaximeterFixedListItemComponent } from './taximeter-fixed-list-item.component';

describe('Component Tests', () => {
  describe('TaximeterFixedListItem Management Component', () => {
    let comp: TaximeterFixedListItemComponent;
    let fixture: ComponentFixture<TaximeterFixedListItemComponent>;
    let service: TaximeterFixedListItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFixedListItemComponent],
      })
        .overrideTemplate(TaximeterFixedListItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFixedListItemComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterFixedListItemService);

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
      expect(comp.taximeterFixedListItems?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
