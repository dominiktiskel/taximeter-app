jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterFixedListItem, TaximeterFixedListItem } from '../taximeter-fixed-list-item.model';
import { TaximeterFixedListItemService } from '../service/taximeter-fixed-list-item.service';

import { TaximeterFixedListItemRoutingResolveService } from './taximeter-fixed-list-item-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterFixedListItem routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterFixedListItemRoutingResolveService;
    let service: TaximeterFixedListItemService;
    let resultTaximeterFixedListItem: ITaximeterFixedListItem | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterFixedListItemRoutingResolveService);
      service = TestBed.inject(TaximeterFixedListItemService);
      resultTaximeterFixedListItem = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterFixedListItem returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFixedListItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFixedListItem).toEqual({ id: 123 });
      });

      it('should return new ITaximeterFixedListItem if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFixedListItem = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterFixedListItem).toEqual(new TaximeterFixedListItem());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFixedListItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFixedListItem).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
