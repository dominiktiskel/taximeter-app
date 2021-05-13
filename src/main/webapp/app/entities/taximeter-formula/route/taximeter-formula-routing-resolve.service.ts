import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterFormula, TaximeterFormula } from '../taximeter-formula.model';
import { TaximeterFormulaService } from '../service/taximeter-formula.service';

@Injectable({ providedIn: 'root' })
export class TaximeterFormulaRoutingResolveService implements Resolve<ITaximeterFormula> {
  constructor(protected service: TaximeterFormulaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterFormula> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterFormula: HttpResponse<TaximeterFormula>) => {
          if (taximeterFormula.body) {
            return of(taximeterFormula.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterFormula());
  }
}
