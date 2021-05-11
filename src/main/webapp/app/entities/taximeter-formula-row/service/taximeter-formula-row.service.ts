import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaximeterFormulaRow, getTaximeterFormulaRowIdentifier } from '../taximeter-formula-row.model';

export type EntityResponseType = HttpResponse<ITaximeterFormulaRow>;
export type EntityArrayResponseType = HttpResponse<ITaximeterFormulaRow[]>;

@Injectable({ providedIn: 'root' })
export class TaximeterFormulaRowService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/taximeter-formula-rows');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taximeterFormulaRow: ITaximeterFormulaRow): Observable<EntityResponseType> {
    return this.http.post<ITaximeterFormulaRow>(this.resourceUrl, taximeterFormulaRow, { observe: 'response' });
  }

  update(taximeterFormulaRow: ITaximeterFormulaRow): Observable<EntityResponseType> {
    return this.http.put<ITaximeterFormulaRow>(
      `${this.resourceUrl}/${getTaximeterFormulaRowIdentifier(taximeterFormulaRow) as number}`,
      taximeterFormulaRow,
      { observe: 'response' }
    );
  }

  partialUpdate(taximeterFormulaRow: ITaximeterFormulaRow): Observable<EntityResponseType> {
    return this.http.patch<ITaximeterFormulaRow>(
      `${this.resourceUrl}/${getTaximeterFormulaRowIdentifier(taximeterFormulaRow) as number}`,
      taximeterFormulaRow,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaximeterFormulaRow>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaximeterFormulaRow[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaximeterFormulaRowToCollectionIfMissing(
    taximeterFormulaRowCollection: ITaximeterFormulaRow[],
    ...taximeterFormulaRowsToCheck: (ITaximeterFormulaRow | null | undefined)[]
  ): ITaximeterFormulaRow[] {
    const taximeterFormulaRows: ITaximeterFormulaRow[] = taximeterFormulaRowsToCheck.filter(isPresent);
    if (taximeterFormulaRows.length > 0) {
      const taximeterFormulaRowCollectionIdentifiers = taximeterFormulaRowCollection.map(
        taximeterFormulaRowItem => getTaximeterFormulaRowIdentifier(taximeterFormulaRowItem)!
      );
      const taximeterFormulaRowsToAdd = taximeterFormulaRows.filter(taximeterFormulaRowItem => {
        const taximeterFormulaRowIdentifier = getTaximeterFormulaRowIdentifier(taximeterFormulaRowItem);
        if (taximeterFormulaRowIdentifier == null || taximeterFormulaRowCollectionIdentifiers.includes(taximeterFormulaRowIdentifier)) {
          return false;
        }
        taximeterFormulaRowCollectionIdentifiers.push(taximeterFormulaRowIdentifier);
        return true;
      });
      return [...taximeterFormulaRowsToAdd, ...taximeterFormulaRowCollection];
    }
    return taximeterFormulaRowCollection;
  }
}
