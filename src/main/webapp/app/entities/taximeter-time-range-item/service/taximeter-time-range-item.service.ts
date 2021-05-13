import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterTimeRangeItem, getTaximeterTimeRangeItemIdentifier } from '../taximeter-time-range-item.model';

export type EntityResponseType = HttpResponse<ITaximeterTimeRangeItem>;
export type EntityArrayResponseType = HttpResponse<ITaximeterTimeRangeItem[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterTimeRangeItemService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-time-range-items');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterTimeRangeItem: ITaximeterTimeRangeItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterTimeRangeItem);
    return this.http
      .post<ITaximeterTimeRangeItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterTimeRangeItem: ITaximeterTimeRangeItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterTimeRangeItem);
    return this.http
      .put<ITaximeterTimeRangeItem>(`${this.resourceUrl}/${getTaximeterTimeRangeItemIdentifier(taximeterTimeRangeItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterTimeRangeItem: ITaximeterTimeRangeItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterTimeRangeItem);
    return this.http
      .patch<ITaximeterTimeRangeItem>(
        `${this.resourceUrl}/${getTaximeterTimeRangeItemIdentifier(taximeterTimeRangeItem) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterTimeRangeItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterTimeRangeItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterTimeRangeItemToCollectionIfMissing(
    taximeterTimeRangeItemCollection: ITaximeterTimeRangeItem[],
    ...taximeterTimeRangeItemsToCheck: (ITaximeterTimeRangeItem | null | undefined)[]
  ): ITaximeterTimeRangeItem[] {
    const taximeterTimeRangeItems: ITaximeterTimeRangeItem[] = taximeterTimeRangeItemsToCheck.filter(isPresent);
    if (taximeterTimeRangeItems.length > 0) {
      const taximeterTimeRangeItemCollectionIdentifiers = taximeterTimeRangeItemCollection.map(
        taximeterTimeRangeItemItem => getTaximeterTimeRangeItemIdentifier(taximeterTimeRangeItemItem)!
      );
      const taximeterTimeRangeItemsToAdd = taximeterTimeRangeItems.filter(taximeterTimeRangeItemItem => {
        const taximeterTimeRangeItemIdentifier = getTaximeterTimeRangeItemIdentifier(taximeterTimeRangeItemItem);
        if (
          taximeterTimeRangeItemIdentifier == null ||
          taximeterTimeRangeItemCollectionIdentifiers.includes(taximeterTimeRangeItemIdentifier)
        ) {
          return false;
        }
        taximeterTimeRangeItemCollectionIdentifiers.push(taximeterTimeRangeItemIdentifier);
        return true;
      });
      return [...taximeterTimeRangeItemsToAdd, ...taximeterTimeRangeItemCollection];
    }
    return taximeterTimeRangeItemCollection;
  }

  protected convertDateFromClient(taximeterTimeRangeItem: ITaximeterTimeRangeItem): ITaximeterTimeRangeItem {
    return Object.assign({}, taximeterTimeRangeItem, {
      created: taximeterTimeRangeItem.created?.isValid() ? taximeterTimeRangeItem.created.toJSON() : undefined,
      updated: taximeterTimeRangeItem.updated?.isValid() ? taximeterTimeRangeItem.updated.toJSON() : undefined,
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
      res.body.forEach((taximeterTimeRangeItem: ITaximeterTimeRangeItem) => {
        taximeterTimeRangeItem.created = taximeterTimeRangeItem.created ? dayjs(taximeterTimeRangeItem.created) : undefined;
        taximeterTimeRangeItem.updated = taximeterTimeRangeItem.updated ? dayjs(taximeterTimeRangeItem.updated) : undefined;
      });
    }
    return res;
  }
}
