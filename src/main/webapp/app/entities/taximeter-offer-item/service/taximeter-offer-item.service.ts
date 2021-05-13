import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterOfferItem, getTaximeterOfferItemIdentifier } from '../taximeter-offer-item.model';

export type EntityResponseType = HttpResponse<ITaximeterOfferItem>;
export type EntityArrayResponseType = HttpResponse<ITaximeterOfferItem[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterOfferItemService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-offer-items');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterOfferItem: ITaximeterOfferItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOfferItem);
    return this.http
      .post<ITaximeterOfferItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterOfferItem: ITaximeterOfferItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOfferItem);
    return this.http
      .put<ITaximeterOfferItem>(`${this.resourceUrl}/${getTaximeterOfferItemIdentifier(taximeterOfferItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterOfferItem: ITaximeterOfferItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOfferItem);
    return this.http
      .patch<ITaximeterOfferItem>(`${this.resourceUrl}/${getTaximeterOfferItemIdentifier(taximeterOfferItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterOfferItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterOfferItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterOfferItemToCollectionIfMissing(
    taximeterOfferItemCollection: ITaximeterOfferItem[],
    ...taximeterOfferItemsToCheck: (ITaximeterOfferItem | null | undefined)[]
  ): ITaximeterOfferItem[] {
    const taximeterOfferItems: ITaximeterOfferItem[] = taximeterOfferItemsToCheck.filter(isPresent);
    if (taximeterOfferItems.length > 0) {
      const taximeterOfferItemCollectionIdentifiers = taximeterOfferItemCollection.map(
        taximeterOfferItemItem => getTaximeterOfferItemIdentifier(taximeterOfferItemItem)!
      );
      const taximeterOfferItemsToAdd = taximeterOfferItems.filter(taximeterOfferItemItem => {
        const taximeterOfferItemIdentifier = getTaximeterOfferItemIdentifier(taximeterOfferItemItem);
        if (taximeterOfferItemIdentifier == null || taximeterOfferItemCollectionIdentifiers.includes(taximeterOfferItemIdentifier)) {
          return false;
        }
        taximeterOfferItemCollectionIdentifiers.push(taximeterOfferItemIdentifier);
        return true;
      });
      return [...taximeterOfferItemsToAdd, ...taximeterOfferItemCollection];
    }
    return taximeterOfferItemCollection;
  }

  protected convertDateFromClient(taximeterOfferItem: ITaximeterOfferItem): ITaximeterOfferItem {
    return Object.assign({}, taximeterOfferItem, {
      created: taximeterOfferItem.created?.isValid() ? taximeterOfferItem.created.toJSON() : undefined,
      updated: taximeterOfferItem.updated?.isValid() ? taximeterOfferItem.updated.toJSON() : undefined,
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
      res.body.forEach((taximeterOfferItem: ITaximeterOfferItem) => {
        taximeterOfferItem.created = taximeterOfferItem.created ? dayjs(taximeterOfferItem.created) : undefined;
        taximeterOfferItem.updated = taximeterOfferItem.updated ? dayjs(taximeterOfferItem.updated) : undefined;
      });
    }
    return res;
  }
}
