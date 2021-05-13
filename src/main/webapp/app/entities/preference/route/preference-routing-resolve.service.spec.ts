jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPreference, Preference } from '../preference.model';
import { PreferenceService } from '../service/preference.service';

import { PreferenceRoutingResolveService } from './preference-routing-resolve.service';

describe('Service Tests', () => {
  describe('Preference routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PreferenceRoutingResolveService;
    let service: PreferenceService;
    let resultPreference: IPreference | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PreferenceRoutingResolveService);
      service = TestBed.inject(PreferenceService);
      resultPreference = undefined;
    });

    describe('resolve', () => {
      it('should return IPreference returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPreference = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPreference).toEqual({ id: 123 });
      });

      it('should return new IPreference if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPreference = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPreference).toEqual(new Preference());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPreference = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPreference).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
