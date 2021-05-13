import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterOfferItem, TaximeterOfferItem } from '../taximeter-offer-item.model';
import { TaximeterOfferItemService } from '../service/taximeter-offer-item.service';

@Injectable({ providedIn: 'root' })
export class TaximeterOfferItemRoutingResolveService implements Resolve<ITaximeterOfferItem> {
  constructor(protected service: TaximeterOfferItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterOfferItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterOfferItem: HttpResponse<TaximeterOfferItem>) => {
          if (taximeterOfferItem.body) {
            return of(taximeterOfferItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterOfferItem());
  }
}
