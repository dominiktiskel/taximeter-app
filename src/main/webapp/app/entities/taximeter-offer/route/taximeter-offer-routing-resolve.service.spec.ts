jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterOffer, TaximeterOffer } from '../taximeter-offer.model';
import { TaximeterOfferService } from '../service/taximeter-offer.service';

import { TaximeterOfferRoutingResolveService } from './taximeter-offer-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterOffer routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterOfferRoutingResolveService;
    let service: TaximeterOfferService;
    let resultTaximeterOffer: ITaximeterOffer | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterOfferRoutingResolveService);
      service = TestBed.inject(TaximeterOfferService);
      resultTaximeterOffer = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterOffer returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterOffer = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterOffer).toEqual({ id: 123 });
      });

      it('should return new ITaximeterOffer if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterOffer = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterOffer).toEqual(new TaximeterOffer());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterOffer = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterOffer).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
