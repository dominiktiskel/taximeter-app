import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITaximeterFixedListItem, TaximeterFixedListItem } from '../taximeter-fixed-list-item.model';

import { TaximeterFixedListItemService } from './taximeter-fixed-list-item.service';

describe('Service Tests', () => {
  describe('TaximeterFixedListItem Service', () => {
    let service: TaximeterFixedListItemService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterFixedListItem;
    let expectedResult: ITaximeterFixedListItem | ITaximeterFixedListItem[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterFixedListItemService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        from: 'AAAAAAA',
        to: 'AAAAAAA',
        value: 0,
        valueReverse: 0,
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

      it('should create a TaximeterFixedListItem', () => {
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

        service.create(new TaximeterFixedListItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterFixedListItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            from: 'BBBBBB',
            to: 'BBBBBB',
            value: 1,
            valueReverse: 1,
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

      it('should partial update a TaximeterFixedListItem', () => {
        const patchObject = Object.assign(
          {
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          new TaximeterFixedListItem()
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

      it('should return a list of TaximeterFixedListItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            from: 'BBBBBB',
            to: 'BBBBBB',
            value: 1,
            valueReverse: 1,
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

      it('should delete a TaximeterFixedListItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterFixedListItemToCollectionIfMissing', () => {
        it('should add a TaximeterFixedListItem to an empty array', () => {
          const taximeterFixedListItem: ITaximeterFixedListItem = { id: 123 };
          expectedResult = service.addTaximeterFixedListItemToCollectionIfMissing([], taximeterFixedListItem);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFixedListItem);
        });

        it('should not add a TaximeterFixedListItem to an array that contains it', () => {
          const taximeterFixedListItem: ITaximeterFixedListItem = { id: 123 };
          const taximeterFixedListItemCollection: ITaximeterFixedListItem[] = [
            {
              ...taximeterFixedListItem,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterFixedListItemToCollectionIfMissing(taximeterFixedListItemCollection, taximeterFixedListItem);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterFixedListItem to an array that doesn't contain it", () => {
          const taximeterFixedListItem: ITaximeterFixedListItem = { id: 123 };
          const taximeterFixedListItemCollection: ITaximeterFixedListItem[] = [{ id: 456 }];
          expectedResult = service.addTaximeterFixedListItemToCollectionIfMissing(taximeterFixedListItemCollection, taximeterFixedListItem);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFixedListItem);
        });

        it('should add only unique TaximeterFixedListItem to an array', () => {
          const taximeterFixedListItemArray: ITaximeterFixedListItem[] = [{ id: 123 }, { id: 456 }, { id: 82581 }];
          const taximeterFixedListItemCollection: ITaximeterFixedListItem[] = [{ id: 123 }];
          expectedResult = service.addTaximeterFixedListItemToCollectionIfMissing(
            taximeterFixedListItemCollection,
            ...taximeterFixedListItemArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterFixedListItem: ITaximeterFixedListItem = { id: 123 };
          const taximeterFixedListItem2: ITaximeterFixedListItem = { id: 456 };
          expectedResult = service.addTaximeterFixedListItemToCollectionIfMissing([], taximeterFixedListItem, taximeterFixedListItem2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFixedListItem);
          expect(expectedResult).toContain(taximeterFixedListItem2);
        });

        it('should accept null and undefined values', () => {
          const taximeterFixedListItem: ITaximeterFixedListItem = { id: 123 };
          expectedResult = service.addTaximeterFixedListItemToCollectionIfMissing([], null, taximeterFixedListItem, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFixedListItem);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
