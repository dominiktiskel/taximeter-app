import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITaximeterTimeRange, TaximeterTimeRange } from '../taximeter-time-range.model';

import { TaximeterTimeRangeService } from './taximeter-time-range.service';

describe('Service Tests', () => {
  describe('TaximeterTimeRange Service', () => {
    let service: TaximeterTimeRangeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterTimeRange;
    let expectedResult: ITaximeterTimeRange | ITaximeterTimeRange[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterTimeRangeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        created: currentDate,
        updated: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TaximeterTimeRange', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new TaximeterTimeRange()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterTimeRange', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TaximeterTimeRange', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
          },
          new TaximeterTimeRange()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TaximeterTimeRange', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TaximeterTimeRange', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterTimeRangeToCollectionIfMissing', () => {
        it('should add a TaximeterTimeRange to an empty array', () => {
          const taximeterTimeRange: ITaximeterTimeRange = { id: 123 };
          expectedResult = service.addTaximeterTimeRangeToCollectionIfMissing([], taximeterTimeRange);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterTimeRange);
        });

        it('should not add a TaximeterTimeRange to an array that contains it', () => {
          const taximeterTimeRange: ITaximeterTimeRange = { id: 123 };
          const taximeterTimeRangeCollection: ITaximeterTimeRange[] = [
            {
              ...taximeterTimeRange,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterTimeRangeToCollectionIfMissing(taximeterTimeRangeCollection, taximeterTimeRange);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterTimeRange to an array that doesn't contain it", () => {
          const taximeterTimeRange: ITaximeterTimeRange = { id: 123 };
          const taximeterTimeRangeCollection: ITaximeterTimeRange[] = [{ id: 456 }];
          expectedResult = service.addTaximeterTimeRangeToCollectionIfMissing(taximeterTimeRangeCollection, taximeterTimeRange);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterTimeRange);
        });

        it('should add only unique TaximeterTimeRange to an array', () => {
          const taximeterTimeRangeArray: ITaximeterTimeRange[] = [{ id: 123 }, { id: 456 }, { id: 12273 }];
          const taximeterTimeRangeCollection: ITaximeterTimeRange[] = [{ id: 123 }];
          expectedResult = service.addTaximeterTimeRangeToCollectionIfMissing(taximeterTimeRangeCollection, ...taximeterTimeRangeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterTimeRange: ITaximeterTimeRange = { id: 123 };
          const taximeterTimeRange2: ITaximeterTimeRange = { id: 456 };
          expectedResult = service.addTaximeterTimeRangeToCollectionIfMissing([], taximeterTimeRange, taximeterTimeRange2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterTimeRange);
          expect(expectedResult).toContain(taximeterTimeRange2);
        });

        it('should accept null and undefined values', () => {
          const taximeterTimeRange: ITaximeterTimeRange = { id: 123 };
          expectedResult = service.addTaximeterTimeRangeToCollectionIfMissing([], null, taximeterTimeRange, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterTimeRange);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
