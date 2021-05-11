import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<ITaximeterFormula>(this.resourceUrl, taximeterFormula, { observe: 'response' });
  }

  update(taximeterFormula: ITaximeterFormula): Observable<EntityResponseType> {
    return this.http.put<ITaximeterFormula>(
      `${this.resourceUrl}/${getTaximeterFormulaIdentifier(taximeterFormula) as number}`,
      taximeterFormula,
      { observe: 'response' }
    );
  }

  partialUpdate(taximeterFormula: ITaximeterFormula): Observable<EntityResponseType> {
    return this.http.patch<ITaximeterFormula>(
      `${this.resourceUrl}/${getTaximeterFormulaIdentifier(taximeterFormula) as number}`,
      taximeterFormula,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaximeterFormula>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaximeterFormula[]>(this.resourceUrl, { params: options, observe: 'response' });
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
}
