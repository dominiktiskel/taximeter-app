import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterTimeRange, TaximeterTimeRange } from '../taximeter-time-range.model';
import { TaximeterTimeRangeService } from '../service/taximeter-time-range.service';

@Injectable({ providedIn: 'root' })
export class TaximeterTimeRangeRoutingResolveService implements Resolve<ITaximeterTimeRange> {
  constructor(protected service: TaximeterTimeRangeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterTimeRange> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterTimeRange: HttpResponse<TaximeterTimeRange>) => {
          if (taximeterTimeRange.body) {
            return of(taximeterTimeRange.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterTimeRange());
  }
}
