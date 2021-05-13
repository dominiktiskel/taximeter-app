jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterFixedList, TaximeterFixedList } from '../taximeter-fixed-list.model';
import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';

import { TaximeterFixedListRoutingResolveService } from './taximeter-fixed-list-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterFixedList routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterFixedListRoutingResolveService;
    let service: TaximeterFixedListService;
    let resultTaximeterFixedList: ITaximeterFixedList | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterFixedListRoutingResolveService);
      service = TestBed.inject(TaximeterFixedListService);
      resultTaximeterFixedList = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterFixedList returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFixedList = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFixedList).toEqual({ id: 123 });
      });

      it('should return new ITaximeterFixedList if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFixedList = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterFixedList).toEqual(new TaximeterFixedList());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFixedList = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFixedList).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
