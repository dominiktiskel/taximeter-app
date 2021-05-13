jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterOfferGroup, TaximeterOfferGroup } from '../taximeter-offer-group.model';
import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';

import { TaximeterOfferGroupRoutingResolveService } from './taximeter-offer-group-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterOfferGroup routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterOfferGroupRoutingResolveService;
    let service: TaximeterOfferGroupService;
    let resultTaximeterOfferGroup: ITaximeterOfferGroup | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterOfferGroupRoutingResolveService);
      service = TestBed.inject(TaximeterOfferGroupService);
      resultTaximeterOfferGroup = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterOfferGroup returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterOfferGroup = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterOfferGroup).toEqual({ id: 123 });
      });

      it('should return new ITaximeterOfferGroup if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterOfferGroup = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterOfferGroup).toEqual(new TaximeterOfferGroup());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterOfferGroup = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterOfferGroup).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
