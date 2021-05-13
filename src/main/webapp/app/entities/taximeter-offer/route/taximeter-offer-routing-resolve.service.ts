import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaximeterOffer, TaximeterOffer } from '../taximeter-offer.model';
import { TaximeterOfferService } from '../service/taximeter-offer.service';

@Injectable({ providedIn: 'root' })
export class TaximeterOfferRoutingResolveService implements Resolve<ITaximeterOffer> {
  constructor(protected service: TaximeterOfferService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaximeterOffer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taximeterOffer: HttpResponse<TaximeterOffer>) => {
          if (taximeterOffer.body) {
            return of(taximeterOffer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaximeterOffer());
  }
}
