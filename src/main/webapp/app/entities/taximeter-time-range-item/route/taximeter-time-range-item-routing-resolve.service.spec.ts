jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterTimeRangeItem, TaximeterTimeRangeItem } from '../taximeter-time-range-item.model';
import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';

import { TaximeterTimeRangeItemRoutingResolveService } from './taximeter-time-range-item-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterTimeRangeItem routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterTimeRangeItemRoutingResolveService;
    let service: TaximeterTimeRangeItemService;
    let resultTaximeterTimeRangeItem: ITaximeterTimeRangeItem | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterTimeRangeItemRoutingResolveService);
      service = TestBed.inject(TaximeterTimeRangeItemService);
      resultTaximeterTimeRangeItem = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterTimeRangeItem returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterTimeRangeItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterTimeRangeItem).toEqual({ id: 123 });
      });

      it('should return new ITaximeterTimeRangeItem if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterTimeRangeItem = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterTimeRangeItem).toEqual(new TaximeterTimeRangeItem());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterTimeRangeItem = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterTimeRangeItem).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
