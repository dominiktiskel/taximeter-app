import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPreference, Preference } from '../preference.model';
import { PreferenceService } from '../service/preference.service';

@Injectable({ providedIn: 'root' })
export class PreferenceRoutingResolveService implements Resolve<IPreference> {
  constructor(protected service: PreferenceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPreference> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((preference: HttpResponse<Preference>) => {
          if (preference.body) {
            return of(preference.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Preference());
  }
}
