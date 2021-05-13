import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaximeterFixedListItemDetailComponent } from './taximeter-fixed-list-item-detail.component';

describe('Component Tests', () => {
  describe('TaximeterFixedListItem Management Detail Component', () => {
    let comp: TaximeterFixedListItemDetailComponent;
    let fixture: ComponentFixture<TaximeterFixedListItemDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaximeterFixedListItemDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taximeterFixedListItem: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaximeterFixedListItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaximeterFixedListItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taximeterFixedListItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taximeterFixedListItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
