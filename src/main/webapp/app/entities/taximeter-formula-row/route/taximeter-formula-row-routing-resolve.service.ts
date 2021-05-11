import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterFormulaRow, TaximeterFormulaRow } from '../taximeter-formula-row.model';
import { TaximeterFormulaRowService } from '../service/taximeter-formula-row.service';

@Injectable({ providedIn: 'root' })
export class TaximeterFormulaRowRoutingResolveService implements Resolve<ITaximeterFormulaRow> {
  constructor(protected service: TaximeterFormulaRowService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterFormulaRow> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterFormulaRow: HttpResponse<TaximeterFormulaRow>) => {
          if (taximeterFormulaRow.body) {
            return of(taximeterFormulaRow.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterFormulaRow());
  }
}
