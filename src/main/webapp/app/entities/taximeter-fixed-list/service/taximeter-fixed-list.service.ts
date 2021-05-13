import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterFixedList, getTaximeterFixedListIdentifier } from '../taximeter-fixed-list.model';

export type EntityResponseType = HttpResponse<ITaximeterFixedList>;
export type EntityArrayResponseType = HttpResponse<ITaximeterFixedList[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterFixedListService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-fixed-lists');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterFixedList: ITaximeterFixedList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFixedList);
    return this.http
      .post<ITaximeterFixedList>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterFixedList: ITaximeterFixedList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFixedList);
    return this.http
      .put<ITaximeterFixedList>(`${this.resourceUrl}/${getTaximeterFixedListIdentifier(taximeterFixedList) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterFixedList: ITaximeterFixedList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFixedList);
    return this.http
      .patch<ITaximeterFixedList>(`${this.resourceUrl}/${getTaximeterFixedListIdentifier(taximeterFixedList) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterFixedList>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterFixedList[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterFixedListToCollectionIfMissing(
    taximeterFixedListCollection: ITaximeterFixedList[],
    ...taximeterFixedListsToCheck: (ITaximeterFixedList | null | undefined)[]
  ): ITaximeterFixedList[] {
    const taximeterFixedLists: ITaximeterFixedList[] = taximeterFixedListsToCheck.filter(isPresent);
    if (taximeterFixedLists.length > 0) {
      const taximeterFixedListCollectionIdentifiers = taximeterFixedListCollection.map(
        taximeterFixedListItem => getTaximeterFixedListIdentifier(taximeterFixedListItem)!
      );
      const taximeterFixedListsToAdd = taximeterFixedLists.filter(taximeterFixedListItem => {
        const taximeterFixedListIdentifier = getTaximeterFixedListIdentifier(taximeterFixedListItem);
        if (taximeterFixedListIdentifier == null || taximeterFixedListCollectionIdentifiers.includes(taximeterFixedListIdentifier)) {
          return false;
        }
        taximeterFixedListCollectionIdentifiers.push(taximeterFixedListIdentifier);
        return true;
      });
      return [...taximeterFixedListsToAdd, ...taximeterFixedListCollection];
    }
    return taximeterFixedListCollection;
  }

  protected convertDateFromClient(taximeterFixedList: ITaximeterFixedList): ITaximeterFixedList {
    return Object.assign({}, taximeterFixedList, {
      created: taximeterFixedList.created?.isValid() ? taximeterFixedList.created.toJSON() : undefined,
      updated: taximeterFixedList.updated?.isValid() ? taximeterFixedList.updated.toJSON() : undefined,
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
      res.body.forEach((taximeterFixedList: ITaximeterFixedList) => {
        taximeterFixedList.created = taximeterFixedList.created ? dayjs(taximeterFixedList.created) : undefined;
        taximeterFixedList.updated = taximeterFixedList.updated ? dayjs(taximeterFixedList.updated) : undefined;
      });
    }
    return res;
  }
}
