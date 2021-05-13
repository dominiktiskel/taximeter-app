import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterOffer, getTaximeterOfferIdentifier } from '../taximeter-offer.model';

export type EntityResponseType = HttpResponse<ITaximeterOffer>;
export type EntityArrayResponseType = HttpResponse<ITaximeterOffer[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterOfferService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-offers');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterOffer: ITaximeterOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOffer);
    return this.http
      .post<ITaximeterOffer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterOffer: ITaximeterOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOffer);
    return this.http
      .put<ITaximeterOffer>(`${this.resourceUrl}/${getTaximeterOfferIdentifier(taximeterOffer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterOffer: ITaximeterOffer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterOffer);
    return this.http
      .patch<ITaximeterOffer>(`${this.resourceUrl}/${getTaximeterOfferIdentifier(taximeterOffer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterOffer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterOfferToCollectionIfMissing(
    taximeterOfferCollection: ITaximeterOffer[],
    ...taximeterOffersToCheck: (ITaximeterOffer | null | undefined)[]
  ): ITaximeterOffer[] {
    const taximeterOffers: ITaximeterOffer[] = taximeterOffersToCheck.filter(isPresent);
    if (taximeterOffers.length > 0) {
      const taximeterOfferCollectionIdentifiers = taximeterOfferCollection.map(
        taximeterOfferItem => getTaximeterOfferIdentifier(taximeterOfferItem)!
      );
      const taximeterOffersToAdd = taximeterOffers.filter(taximeterOfferItem => {
        const taximeterOfferIdentifier = getTaximeterOfferIdentifier(taximeterOfferItem);
        if (taximeterOfferIdentifier == null || taximeterOfferCollectionIdentifiers.includes(taximeterOfferIdentifier)) {
          return false;
        }
        taximeterOfferCollectionIdentifiers.push(taximeterOfferIdentifier);
        return true;
      });
      return [...taximeterOffersToAdd, ...taximeterOfferCollection];
    }
    return taximeterOfferCollection;
  }

  protected convertDateFromClient(taximeterOffer: ITaximeterOffer): ITaximeterOffer {
    return Object.assign({}, taximeterOffer, {
      validFrom: taximeterOffer.validFrom?.isValid() ? taximeterOffer.validFrom.toJSON() : undefined,
      validTo: taximeterOffer.validTo?.isValid() ? taximeterOffer.validTo.toJSON() : undefined,
      created: taximeterOffer.created?.isValid() ? taximeterOffer.created.toJSON() : undefined,
      updated: taximeterOffer.updated?.isValid() ? taximeterOffer.updated.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validFrom = res.body.validFrom ? dayjs(res.body.validFrom) : undefined;
      res.body.validTo = res.body.validTo ? dayjs(res.body.validTo) : undefined;
      res.body.created = res.body.created ? dayjs(res.body.created) : undefined;
      res.body.updated = res.body.updated ? dayjs(res.body.updated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taximeterOffer: ITaximeterOffer) => {
        taximeterOffer.validFrom = taximeterOffer.validFrom ? dayjs(taximeterOffer.validFrom) : undefined;
        taximeterOffer.validTo = taximeterOffer.validTo ? dayjs(taximeterOffer.validTo) : undefined;
        taximeterOffer.created = taximeterOffer.created ? dayjs(taximeterOffer.created) : undefined;
        taximeterOffer.updated = taximeterOffer.updated ? dayjs(taximeterOffer.updated) : undefined;
      });
    }
    return res;
  }
}
