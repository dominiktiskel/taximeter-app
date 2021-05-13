import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterFixedListItem, getTaximeterFixedListItemIdentifier } from '../taximeter-fixed-list-item.model';

export type EntityResponseType = HttpResponse<ITaximeterFixedListItem>;
export type EntityArrayResponseType = HttpResponse<ITaximeterFixedListItem[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterFixedListItemService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-fixed-list-items');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterFixedListItem: ITaximeterFixedListItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFixedListItem);
    return this.http
      .post<ITaximeterFixedListItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterFixedListItem: ITaximeterFixedListItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFixedListItem);
    return this.http
      .put<ITaximeterFixedListItem>(`${this.resourceUrl}/${getTaximeterFixedListItemIdentifier(taximeterFixedListItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterFixedListItem: ITaximeterFixedListItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFixedListItem);
    return this.http
      .patch<ITaximeterFixedListItem>(
        `${this.resourceUrl}/${getTaximeterFixedListItemIdentifier(taximeterFixedListItem) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterFixedListItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterFixedListItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterFixedListItemToCollectionIfMissing(
    taximeterFixedListItemCollection: ITaximeterFixedListItem[],
    ...taximeterFixedListItemsToCheck: (ITaximeterFixedListItem | null | undefined)[]
  ): ITaximeterFixedListItem[] {
    const taximeterFixedListItems: ITaximeterFixedListItem[] = taximeterFixedListItemsToCheck.filter(isPresent);
    if (taximeterFixedListItems.length > 0) {
      const taximeterFixedListItemCollectionIdentifiers = taximeterFixedListItemCollection.map(
        taximeterFixedListItemItem => getTaximeterFixedListItemIdentifier(taximeterFixedListItemItem)!
      );
      const taximeterFixedListItemsToAdd = taximeterFixedListItems.filter(taximeterFixedListItemItem => {
        const taximeterFixedListItemIdentifier = getTaximeterFixedListItemIdentifier(taximeterFixedListItemItem);
        if (
          taximeterFixedListItemIdentifier == null ||
          taximeterFixedListItemCollectionIdentifiers.includes(taximeterFixedListItemIdentifier)
        ) {
          return false;
        }
        taximeterFixedListItemCollectionIdentifiers.push(taximeterFixedListItemIdentifier);
        return true;
      });
      return [...taximeterFixedListItemsToAdd, ...taximeterFixedListItemCollection];
    }
    return taximeterFixedListItemCollection;
  }

  protected convertDateFromClient(taximeterFixedListItem: ITaximeterFixedListItem): ITaximeterFixedListItem {
    return Object.assign({}, taximeterFixedListItem, {
      created: taximeterFixedListItem.created?.isValid() ? taximeterFixedListItem.created.toJSON() : undefined,
      updated: taximeterFixedListItem.updated?.isValid() ? taximeterFixedListItem.updated.toJSON() : undefined,
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
      res.body.forEach((taximeterFixedListItem: ITaximeterFixedListItem) => {
        taximeterFixedListItem.created = taximeterFixedListItem.created ? dayjs(taximeterFixedListItem.created) : undefined;
        taximeterFixedListItem.updated = taximeterFixedListItem.updated ? dayjs(taximeterFixedListItem.updated) : undefined;
      });
    }
    return res;
  }
}
