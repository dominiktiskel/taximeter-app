jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterTimeRange, TaximeterTimeRange } from '../taximeter-time-range.model';
import { TaximeterTimeRangeService } from '../service/taximeter-time-range.service';

import { TaximeterTimeRangeRoutingResolveService } from './taximeter-time-range-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterTimeRange routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterTimeRangeRoutingResolveService;
    let service: TaximeterTimeRangeService;
    let resultTaximeterTimeRange: ITaximeterTimeRange | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterTimeRangeRoutingResolveService);
      service = TestBed.inject(TaximeterTimeRangeService);
      resultTaximeterTimeRange = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterTimeRange returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterTimeRange = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterTimeRange).toEqual({ id: 123 });
      });

      it('should return new ITaximeterTimeRange if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterTimeRange = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterTimeRange).toEqual(new TaximeterTimeRange());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterTimeRange = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterTimeRange).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
