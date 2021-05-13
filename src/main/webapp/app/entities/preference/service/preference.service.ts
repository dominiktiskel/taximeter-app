import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPreference, getPreferenceIdentifier } from '../preference.model';

export type EntityResponseType = HttpResponse<IPreference>;
export type EntityArrayResponseType = HttpResponse<IPreference[]>;

@Injectable({ providedIn: 'root' })
export class PreferenceService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/preferences');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(preference: IPreference): Observable<EntityResponseType> {
    return this.http.post<IPreference>(this.resourceUrl, preference, { observe: 'response' });
  }

  update(preference: IPreference): Observable<EntityResponseType> {
    return this.http.put<IPreference>(`${this.resourceUrl}/${getPreferenceIdentifier(preference) as number}`, preference, {
      observe: 'response',
    });
  }

  partialUpdate(preference: IPreference): Observable<EntityResponseType> {
    return this.http.patch<IPreference>(`${this.resourceUrl}/${getPreferenceIdentifier(preference) as number}`, preference, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPreference>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPreference[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPreferenceToCollectionIfMissing(
    preferenceCollection: IPreference[],
    ...preferencesToCheck: (IPreference | null | undefined)[]
  ): IPreference[] {
    const preferences: IPreference[] = preferencesToCheck.filter(isPresent);
    if (preferences.length > 0) {
      const preferenceCollectionIdentifiers = preferenceCollection.map(preferenceItem => getPreferenceIdentifier(preferenceItem)!);
      const preferencesToAdd = preferences.filter(preferenceItem => {
        const preferenceIdentifier = getPreferenceIdentifier(preferenceItem);
        if (preferenceIdentifier == null || preferenceCollectionIdentifiers.includes(preferenceIdentifier)) {
          return false;
        }
        preferenceCollectionIdentifiers.push(preferenceIdentifier);
        return true;
      });
      return [...preferencesToAdd, ...preferenceCollection];
    }
    return preferenceCollection;
  }
}
