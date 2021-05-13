import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterTimeRangeItem, TaximeterTimeRangeItem } from '../taximeter-time-range-item.model';
import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';

@Injectable({ providedIn: 'root' })
export class TaximeterTimeRangeItemRoutingResolveService implements Resolve<ITaximeterTimeRangeItem> {
  constructor(protected service: TaximeterTimeRangeItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterTimeRangeItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterTimeRangeItem: HttpResponse<TaximeterTimeRangeItem>) => {
          if (taximeterTimeRangeItem.body) {
            return of(taximeterTimeRangeItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterTimeRangeItem());
  }
}
