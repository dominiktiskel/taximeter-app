jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaximeterFormula, TaximeterFormula } from '../taximeter-formula.model';
import { TaximeterFormulaService } from '../service/taximeter-formula.service';

import { TaximeterFormulaRoutingResolveService } from './taximeter-formula-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaximeterFormula routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaximeterFormulaRoutingResolveService;
    let service: TaximeterFormulaService;
    let resultTaximeterFormula: ITaximeterFormula | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaximeterFormulaRoutingResolveService);
      service = TestBed.inject(TaximeterFormulaService);
      resultTaximeterFormula = undefined;
    });

    describe('resolve', () => {
      it('should return ITaximeterFormula returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFormula = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFormula).toEqual({ id: 123 });
      });

      it('should return new ITaximeterFormula if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFormula = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaximeterFormula).toEqual(new TaximeterFormula());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaximeterFormula = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaximeterFormula).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
