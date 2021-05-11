import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TaximeterFormulaRowType } from 'app/entities/enumerations/taximeter-formula-row-type.model';
import { ITaximeterFormulaRow, TaximeterFormulaRow } from '../taximeter-formula-row.model';

import { TaximeterFormulaRowService } from './taximeter-formula-row.service';

describe('Service Tests', () => {
  describe('TaximeterFormulaRow Service', () => {
    let service: TaximeterFormulaRowService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterFormulaRow;
    let expectedResult: ITaximeterFormulaRow | ITaximeterFormulaRow[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterFormulaRowService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        type: TaximeterFormulaRowType.FLAT_RATE,
        value: 0,
        step: 0,
        granulation: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TaximeterFormulaRow', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TaximeterFormulaRow()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterFormulaRow', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            type: 'BBBBBB',
            value: 1,
            step: 1,
            granulation: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TaximeterFormulaRow', () => {
        const patchObject = Object.assign(
          {
            type: 'BBBBBB',
            value: 1,
            step: 1,
            granulation: 1,
          },
          new TaximeterFormulaRow()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TaximeterFormulaRow', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            type: 'BBBBBB',
            value: 1,
            step: 1,
            granulation: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TaximeterFormulaRow', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterFormulaRowToCollectionIfMissing', () => {
        it('should add a TaximeterFormulaRow to an empty array', () => {
          const taximeterFormulaRow: ITaximeterFormulaRow = { id: 123 };
          expectedResult = service.addTaximeterFormulaRowToCollectionIfMissing([], taximeterFormulaRow);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFormulaRow);
        });

        it('should not add a TaximeterFormulaRow to an array that contains it', () => {
          const taximeterFormulaRow: ITaximeterFormulaRow = { id: 123 };
          const taximeterFormulaRowCollection: ITaximeterFormulaRow[] = [
            {
              ...taximeterFormulaRow,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterFormulaRowToCollectionIfMissing(taximeterFormulaRowCollection, taximeterFormulaRow);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterFormulaRow to an array that doesn't contain it", () => {
          const taximeterFormulaRow: ITaximeterFormulaRow = { id: 123 };
          const taximeterFormulaRowCollection: ITaximeterFormulaRow[] = [{ id: 456 }];
          expectedResult = service.addTaximeterFormulaRowToCollectionIfMissing(taximeterFormulaRowCollection, taximeterFormulaRow);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFormulaRow);
        });

        it('should add only unique TaximeterFormulaRow to an array', () => {
          const taximeterFormulaRowArray: ITaximeterFormulaRow[] = [{ id: 123 }, { id: 456 }, { id: 64154 }];
          const taximeterFormulaRowCollection: ITaximeterFormulaRow[] = [{ id: 123 }];
          expectedResult = service.addTaximeterFormulaRowToCollectionIfMissing(taximeterFormulaRowCollection, ...taximeterFormulaRowArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterFormulaRow: ITaximeterFormulaRow = { id: 123 };
          const taximeterFormulaRow2: ITaximeterFormulaRow = { id: 456 };
          expectedResult = service.addTaximeterFormulaRowToCollectionIfMissing([], taximeterFormulaRow, taximeterFormulaRow2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFormulaRow);
          expect(expectedResult).toContain(taximeterFormulaRow2);
        });

        it('should accept null and undefined values', () => {
          const taximeterFormulaRow: ITaximeterFormulaRow = { id: 123 };
          expectedResult = service.addTaximeterFormulaRowToCollectionIfMissing([], null, taximeterFormulaRow, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFormulaRow);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
