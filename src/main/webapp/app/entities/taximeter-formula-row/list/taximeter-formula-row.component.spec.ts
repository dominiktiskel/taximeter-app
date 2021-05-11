import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterFormulaRowService } from '../service/taximeter-formula-row.service';

import { TaximeterFormulaRowComponent } from './taximeter-formula-row.component';

describe('Component Tests', () => {
  describe('TaximeterFormulaRow Management Component', () => {
    let comp: TaximeterFormulaRowComponent;
    let fixture: ComponentFixture<TaximeterFormulaRowComponent>;
    let service: TaximeterFormulaRowService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFormulaRowComponent],
      })
        .overrideTemplate(TaximeterFormulaRowComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFormulaRowComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterFormulaRowService);

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
      expect(comp.taximeterFormulaRows?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
