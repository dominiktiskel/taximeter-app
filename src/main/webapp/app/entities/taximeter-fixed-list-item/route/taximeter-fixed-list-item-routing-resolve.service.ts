import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterFixedListItem, TaximeterFixedListItem } from '../taximeter-fixed-list-item.model';
import { TaximeterFixedListItemService } from '../service/taximeter-fixed-list-item.service';

@Injectable({ providedIn: 'root' })
export class TaximeterFixedListItemRoutingResolveService implements Resolve<ITaximeterFixedListItem> {
  constructor(protected service: TaximeterFixedListItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterFixedListItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterFixedListItem: HttpResponse<TaximeterFixedListItem>) => {
          if (taximeterFixedListItem.body) {
            return of(taximeterFixedListItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterFixedListItem());
  }
}
