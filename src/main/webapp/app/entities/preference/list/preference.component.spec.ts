import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PreferenceService } from '../service/preference.service';

import { PreferenceComponent } from './preference.component';

describe('Component Tests', () => {
  describe('Preference Management Component', () => {
    let comp: PreferenceComponent;
    let fixture: ComponentFixture<PreferenceComponent>;
    let service: PreferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PreferenceComponent],
      })
        .overrideTemplate(PreferenceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PreferenceComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PreferenceService);

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
      expect(comp.preferences?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
