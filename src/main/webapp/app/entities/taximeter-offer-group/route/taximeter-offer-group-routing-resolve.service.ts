import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterOfferGroup, TaximeterOfferGroup } from '../taximeter-offer-group.model';
import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';

@Injectable({ providedIn: 'root' })
export class TaximeterOfferGroupRoutingResolveService implements Resolve<ITaximeterOfferGroup> {
  constructor(protected service: TaximeterOfferGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterOfferGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterOfferGroup: HttpResponse<TaximeterOfferGroup>) => {
          if (taximeterOfferGroup.body) {
            return of(taximeterOfferGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterOfferGroup());
  }
}
