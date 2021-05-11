import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TaximeterFormulaType } from 'app/entities/enumerations/taximeter-formula-type.model';
import { ITaximeterFormula, TaximeterFormula } from '../taximeter-formula.model';

import { TaximeterFormulaService } from './taximeter-formula.service';

describe('Service Tests', () => {
  describe('TaximeterFormula Service', () => {
    let service: TaximeterFormulaService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterFormula;
    let expectedResult: ITaximeterFormula | ITaximeterFormula[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterFormulaService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        type: TaximeterFormulaType.DISTANCE,
        name: 'AAAAAAA',
        active: false,
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

      it('should create a TaximeterFormula', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TaximeterFormula()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterFormula', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            type: 'BBBBBB',
            name: 'BBBBBB',
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TaximeterFormula', () => {
        const patchObject = Object.assign(
          {
            active: true,
          },
          new TaximeterFormula()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TaximeterFormula', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            type: 'BBBBBB',
            name: 'BBBBBB',
            active: true,
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

      it('should delete a TaximeterFormula', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterFormulaToCollectionIfMissing', () => {
        it('should add a TaximeterFormula to an empty array', () => {
          const taximeterFormula: ITaximeterFormula = { id: 123 };
          expectedResult = service.addTaximeterFormulaToCollectionIfMissing([], taximeterFormula);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFormula);
        });

        it('should not add a TaximeterFormula to an array that contains it', () => {
          const taximeterFormula: ITaximeterFormula = { id: 123 };
          const taximeterFormulaCollection: ITaximeterFormula[] = [
            {
              ...taximeterFormula,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterFormulaToCollectionIfMissing(taximeterFormulaCollection, taximeterFormula);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterFormula to an array that doesn't contain it", () => {
          const taximeterFormula: ITaximeterFormula = { id: 123 };
          const taximeterFormulaCollection: ITaximeterFormula[] = [{ id: 456 }];
          expectedResult = service.addTaximeterFormulaToCollectionIfMissing(taximeterFormulaCollection, taximeterFormula);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFormula);
        });

        it('should add only unique TaximeterFormula to an array', () => {
          const taximeterFormulaArray: ITaximeterFormula[] = [{ id: 123 }, { id: 456 }, { id: 29780 }];
          const taximeterFormulaCollection: ITaximeterFormula[] = [{ id: 123 }];
          expectedResult = service.addTaximeterFormulaToCollectionIfMissing(taximeterFormulaCollection, ...taximeterFormulaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterFormula: ITaximeterFormula = { id: 123 };
          const taximeterFormula2: ITaximeterFormula = { id: 456 };
          expectedResult = service.addTaximeterFormulaToCollectionIfMissing([], taximeterFormula, taximeterFormula2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFormula);
          expect(expectedResult).toContain(taximeterFormula2);
        });

        it('should accept null and undefined values', () => {
          const taximeterFormula: ITaximeterFormula = { id: 123 };
          expectedResult = service.addTaximeterFormulaToCollectionIfMissing([], null, taximeterFormula, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFormula);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
