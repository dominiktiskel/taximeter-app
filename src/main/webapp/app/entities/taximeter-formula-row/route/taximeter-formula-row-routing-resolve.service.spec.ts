jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterFormulaRow, TaximeterFormulaRow } from '../taximeter-formula-row.model';
import { TaximeterFormulaRowService } from '../service/taximeter-formula-row.service';

import { TaximeterFormulaRowRoutingResolveService } from './taximeter-formula-row-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterFormulaRow routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterFormulaRowRoutingResolveService;
    let service: TaximeterFormulaRowService;
    let resultTaximeterFormulaRow: ITaximeterFormulaRow | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterFormulaRowRoutingResolveService);
      service = TestBed.inject(TaximeterFormulaRowService);
      resultTaximeterFormulaRow = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterFormulaRow returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFormulaRow = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFormulaRow).toEqual({ id: 123 });
      });

      it('should return new ITaximeterFormulaRow if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFormulaRow = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterFormulaRow).toEqual(new TaximeterFormulaRow());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFormulaRow = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFormulaRow).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
