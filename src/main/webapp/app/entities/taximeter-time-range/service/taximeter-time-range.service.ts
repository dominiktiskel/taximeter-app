import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterTimeRange, getTaximeterTimeRangeIdentifier } from '../taximeter-time-range.model';

export type EntityResponseType = HttpResponse<ITaximeterTimeRange>;
export type EntityArrayResponseType = HttpResponse<ITaximeterTimeRange[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterTimeRangeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-time-ranges');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterTimeRange: ITaximeterTimeRange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterTimeRange);
    return this.http
      .post<ITaximeterTimeRange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterTimeRange: ITaximeterTimeRange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterTimeRange);
    return this.http
      .put<ITaximeterTimeRange>(`${this.resourceUrl}/${getTaximeterTimeRangeIdentifier(taximeterTimeRange) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterTimeRange: ITaximeterTimeRange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterTimeRange);
    return this.http
      .patch<ITaximeterTimeRange>(`${this.resourceUrl}/${getTaximeterTimeRangeIdentifier(taximeterTimeRange) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterTimeRange>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterTimeRange[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterTimeRangeToCollectionIfMissing(
    taximeterTimeRangeCollection: ITaximeterTimeRange[],
    ...taximeterTimeRangesToCheck: (ITaximeterTimeRange | null | undefined)[]
  ): ITaximeterTimeRange[] {
    const taximeterTimeRanges: ITaximeterTimeRange[] = taximeterTimeRangesToCheck.filter(isPresent);
    if (taximeterTimeRanges.length > 0) {
      const taximeterTimeRangeCollectionIdentifiers = taximeterTimeRangeCollection.map(
        taximeterTimeRangeItem => getTaximeterTimeRangeIdentifier(taximeterTimeRangeItem)!
      );
      const taximeterTimeRangesToAdd = taximeterTimeRanges.filter(taximeterTimeRangeItem => {
        const taximeterTimeRangeIdentifier = getTaximeterTimeRangeIdentifier(taximeterTimeRangeItem);
        if (taximeterTimeRangeIdentifier == null || taximeterTimeRangeCollectionIdentifiers.includes(taximeterTimeRangeIdentifier)) {
          return false;
        }
        taximeterTimeRangeCollectionIdentifiers.push(taximeterTimeRangeIdentifier);
        return true;
      });
      return [...taximeterTimeRangesToAdd, ...taximeterTimeRangeCollection];
    }
    return taximeterTimeRangeCollection;
  }

  protected convertDateFromClient(taximeterTimeRange: ITaximeterTimeRange): ITaximeterTimeRange {
    return Object.assign({}, taximeterTimeRange, {
      created: taximeterTimeRange.created?.isValid() ? taximeterTimeRange.created.toJSON() : undefined,
      updated: taximeterTimeRange.updated?.isValid() ? taximeterTimeRange.updated.toJSON() : undefined,
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
      res.body.forEach((taximeterTimeRange: ITaximeterTimeRange) => {
        taximeterTimeRange.created = taximeterTimeRange.created ? dayjs(taximeterTimeRange.created) : undefined;
        taximeterTimeRange.updated = taximeterTimeRange.updated ? dayjs(taximeterTimeRange.updated) : undefined;
      });
    }
    return res;
  }
}
