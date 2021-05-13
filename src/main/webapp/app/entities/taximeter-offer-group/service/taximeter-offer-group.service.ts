import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterOfferGroup, getTaximeterOfferGroupIdentifier } from '../taximeter-offer-group.model';

export type EntityResponseType = HttpResponse<ITaximeterOfferGroup>;
export type EntityArrayResponseType = HttpResponse<ITaximeterOfferGroup[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterOfferGroupService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-offer-groups');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterOfferGroup: ITaximeterOfferGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOfferGroup);
    return this.http
      .post<ITaximeterOfferGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterOfferGroup: ITaximeterOfferGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOfferGroup);
    return this.http
      .put<ITaximeterOfferGroup>(`${this.resourceUrl}/${getTaximeterOfferGroupIdentifier(taximeterOfferGroup) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterOfferGroup: ITaximeterOfferGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOfferGroup);
    return this.http
      .patch<ITaximeterOfferGroup>(`${this.resourceUrl}/${getTaximeterOfferGroupIdentifier(taximeterOfferGroup) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterOfferGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterOfferGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterOfferGroupToCollectionIfMissing(
    taximeterOfferGroupCollection: ITaximeterOfferGroup[],
    ...taximeterOfferGroupsToCheck: (ITaximeterOfferGroup | null | undefined)[]
  ): ITaximeterOfferGroup[] {
    const taximeterOfferGroups: ITaximeterOfferGroup[] = taximeterOfferGroupsToCheck.filter(isPresent);
    if (taximeterOfferGroups.length > 0) {
      const taximeterOfferGroupCollectionIdentifiers = taximeterOfferGroupCollection.map(
        taximeterOfferGroupItem => getTaximeterOfferGroupIdentifier(taximeterOfferGroupItem)!
      );
      const taximeterOfferGroupsToAdd = taximeterOfferGroups.filter(taximeterOfferGroupItem => {
        const taximeterOfferGroupIdentifier = getTaximeterOfferGroupIdentifier(taximeterOfferGroupItem);
        if (taximeterOfferGroupIdentifier == null || taximeterOfferGroupCollectionIdentifiers.includes(taximeterOfferGroupIdentifier)) {
          return false;
        }
        taximeterOfferGroupCollectionIdentifiers.push(taximeterOfferGroupIdentifier);
        return true;
      });
      return [...taximeterOfferGroupsToAdd, ...taximeterOfferGroupCollection];
    }
    return taximeterOfferGroupCollection;
  }

  protected convertDateFromClient(taximeterOfferGroup: ITaximeterOfferGroup): ITaximeterOfferGroup {
    return Object.assign({}, taximeterOfferGroup, {
      created: taximeterOfferGroup.created?.isValid() ? taximeterOfferGroup.created.toJSON() : undefined,
      updated: taximeterOfferGroup.updated?.isValid() ? taximeterOfferGroup.updated.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? dayjs(res.body.created) : undefined;
      res.body.updated = res.body.updated ? dayjs(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taximeterOfferGroup: ITaximeterOfferGroup) => {
        taximeterOfferGroup.created = taximeterOfferGroup.created ? dayjs(taximeterOfferGroup.created) : undefined;
        taximeterOfferGroup.updated = taximeterOfferGroup.updated ? dayjs(taximeterOfferGroup.updated) : undefined;
      });
    }
    return res;
  }
}
