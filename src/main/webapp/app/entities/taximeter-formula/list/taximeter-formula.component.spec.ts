import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaximeterFormulaService } from '../service/taximeter-formula.service';

import { TaximeterFormulaComponent } from './taximeter-formula.component';

describe('Component Tests', () => {
  describe('TaximeterFormula Management Component', () => {
    let comp: TaximeterFormulaComponent;
    let fixture: ComponentFixture<TaximeterFormulaComponent>;
    let service: TaximeterFormulaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaximeterFormulaComponent],
      })
        .overrideTemplate(TaximeterFormulaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaximeterFormulaComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaximeterFormulaService);

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
      expect(comp.taximeterFormulas?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
