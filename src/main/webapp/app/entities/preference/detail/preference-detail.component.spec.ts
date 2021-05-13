import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PreferenceDetailComponent } from './preference-detail.component';

describe('Component Tests', () => {
  describe('Preference Management Detail Component', () => {
    let comp: PreferenceDetailComponent;
    let fixture: ComponentFixture<PreferenceDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PreferenceDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ preference: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PreferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PreferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load preference on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.preference).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
