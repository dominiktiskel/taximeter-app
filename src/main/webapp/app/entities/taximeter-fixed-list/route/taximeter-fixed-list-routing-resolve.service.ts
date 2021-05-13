import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterFixedList, TaximeterFixedList } from '../taximeter-fixed-list.model';
import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';

@Injectable({ providedIn: 'root' })
export class TaximeterFixedListRoutingResolveService implements Resolve<ITaximeterFixedList> {
  constructor(protected service: TaximeterFixedListService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterFixedList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterFixedList: HttpResponse<TaximeterFixedList>) => {
          if (taximeterFixedList.body) {
            return of(taximeterFixedList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterFixedList());
  }
}
