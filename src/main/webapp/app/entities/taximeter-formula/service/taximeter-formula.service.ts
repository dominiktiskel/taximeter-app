import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterFormula, getTaximeterFormulaIdentifier } from '../taximeter-formula.model';

export type EntityResponseType = HttpResponse<ITaximeterFormula>;
export type EntityArrayResponseType = HttpResponse<ITaximeterFormula[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterFormulaService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-formulas');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterFormula: ITaximeterFormula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFormula);
    return this.http
      .post<ITaximeterFormula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taximeterFormula: ITaximeterFormula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFormula);
    return this.http
      .put<ITaximeterFormula>(`${this.resourceUrl}/${getTaximeterFormulaIdentifier(taximeterFormula) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taximeterFormula: ITaximeterFormula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taximeterFormula);
    return this.http
      .patch<ITaximeterFormula>(`${this.resourceUrl}/${getTaximeterFormulaIdentifier(taximeterFormula) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaximeterFormula>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaximeterFormula[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterFormulaToCollectionIfMissing(
    taximeterFormulaCollection: ITaximeterFormula[],
    ...taximeterFormulasToCheck: (ITaximeterFormula | null | undefined)[]
  ): ITaximeterFormula[] {
    const taximeterFormulas: ITaximeterFormula[] = taximeterFormulasToCheck.filter(isPresent);
    if (taximeterFormulas.length > 0) {
      const taximeterFormulaCollectionIdentifiers = taximeterFormulaCollection.map(
        taximeterFormulaItem => getTaximeterFormulaIdentifier(taximeterFormulaItem)!
      );
      const taximeterFormulasToAdd = taximeterFormulas.filter(taximeterFormulaItem => {
        const taximeterFormulaIdentifier = getTaximeterFormulaIdentifier(taximeterFormulaItem);
        if (taximeterFormulaIdentifier == null || taximeterFormulaCollectionIdentifiers.includes(taximeterFormulaIdentifier)) {
          return false;
        }
        taximeterFormulaCollectionIdentifiers.push(taximeterFormulaIdentifier);
        return true;
      });
      return [...taximeterFormulasToAdd, ...taximeterFormulaCollection];
    }
    return taximeterFormulaCollection;
  }

  protected convertDateFromClient(taximeterFormula: ITaximeterFormula): ITaximeterFormula {
    return Object.assign({}, taximeterFormula, {
      created: taximeterFormula.created?.isValid() ? taximeterFormula.created.toJSON() : undefined,
      updated: taximeterFormula.updated?.isValid() ? taximeterFormula.updated.toJSON() : undefined,
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
      res.body.forEach((taximeterFormula: ITaximeterFormula) => {
        taximeterFormula.created = taximeterFormula.created ? dayjs(taximeterFormula.created) : undefined;
        taximeterFormula.updated = taximeterFormula.updated ? dayjs(taximeterFormula.updated) : undefined;
      });
    }
    return res;
  }
}
