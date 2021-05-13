import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITaximeterTimeRangeItem, TaximeterTimeRangeItem } from '../taximeter-time-range-item.model';

import { TaximeterTimeRangeItemService } from './taximeter-time-range-item.service';

describe('Service Tests', () => {
  describe('TaximeterTimeRangeItem Service', () => {
    let service: TaximeterTimeRangeItemService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterTimeRangeItem;
    let expectedResult: ITaximeterTimeRangeItem | ITaximeterTimeRangeItem[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterTimeRangeItemService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        day: 'AAAAAAA',
        hours: 'AAAAAAA',
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

      it('should create a TaximeterTimeRangeItem', () => {
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

        service.create(new TaximeterTimeRangeItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterTimeRangeItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            day: 'BBBBBB',
            hours: 'BBBBBB',
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

      it('should partial update a TaximeterTimeRangeItem', () => {
        const patchObject = Object.assign(
          {
            day: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          new TaximeterTimeRangeItem()
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

      it('should return a list of TaximeterTimeRangeItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            day: 'BBBBBB',
            hours: 'BBBBBB',
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

      it('should delete a TaximeterTimeRangeItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterTimeRangeItemToCollectionIfMissing', () => {
        it('should add a TaximeterTimeRangeItem to an empty array', () => {
          const taximeterTimeRangeItem: ITaximeterTimeRangeItem = { id: 123 };
          expectedResult = service.addTaximeterTimeRangeItemToCollectionIfMissing([], taximeterTimeRangeItem);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterTimeRangeItem);
        });

        it('should not add a TaximeterTimeRangeItem to an array that contains it', () => {
          const taximeterTimeRangeItem: ITaximeterTimeRangeItem = { id: 123 };
          const taximeterTimeRangeItemCollection: ITaximeterTimeRangeItem[] = [
            {
              ...taximeterTimeRangeItem,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterTimeRangeItemToCollectionIfMissing(taximeterTimeRangeItemCollection, taximeterTimeRangeItem);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterTimeRangeItem to an array that doesn't contain it", () => {
          const taximeterTimeRangeItem: ITaximeterTimeRangeItem = { id: 123 };
          const taximeterTimeRangeItemCollection: ITaximeterTimeRangeItem[] = [{ id: 456 }];
          expectedResult = service.addTaximeterTimeRangeItemToCollectionIfMissing(taximeterTimeRangeItemCollection, taximeterTimeRangeItem);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterTimeRangeItem);
        });

        it('should add only unique TaximeterTimeRangeItem to an array', () => {
          const taximeterTimeRangeItemArray: ITaximeterTimeRangeItem[] = [{ id: 123 }, { id: 456 }, { id: 59032 }];
          const taximeterTimeRangeItemCollection: ITaximeterTimeRangeItem[] = [{ id: 123 }];
          expectedResult = service.addTaximeterTimeRangeItemToCollectionIfMissing(
            taximeterTimeRangeItemCollection,
            ...taximeterTimeRangeItemArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterTimeRangeItem: ITaximeterTimeRangeItem = { id: 123 };
          const taximeterTimeRangeItem2: ITaximeterTimeRangeItem = { id: 456 };
          expectedResult = service.addTaximeterTimeRangeItemToCollectionIfMissing([], taximeterTimeRangeItem, taximeterTimeRangeItem2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterTimeRangeItem);
          expect(expectedResult).toContain(taximeterTimeRangeItem2);
        });

        it('should accept null and undefined values', () => {
          const taximeterTimeRangeItem: ITaximeterTimeRangeItem = { id: 123 };
          expectedResult = service.addTaximeterTimeRangeItemToCollectionIfMissing([], null, taximeterTimeRangeItem, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterTimeRangeItem);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
