import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITaximeterOfferItem, TaximeterOfferItem } from '../taximeter-offer-item.model';

import { TaximeterOfferItemService } from './taximeter-offer-item.service';

describe('Service Tests', () => {
  describe('TaximeterOfferItem Service', () => {
    let service: TaximeterOfferItemService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterOfferItem;
    let expectedResult: ITaximeterOfferItem | ITaximeterOfferItem[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterOfferItemService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        billCompanyPays: 0,
        customerPays: 0,
        taxiGets: 0,
        taxiPays: 0,
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

      it('should create a TaximeterOfferItem', () => {
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

        service.create(new TaximeterOfferItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterOfferItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            billCompanyPays: 1,
            customerPays: 1,
            taxiGets: 1,
            taxiPays: 1,
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

      it('should partial update a TaximeterOfferItem', () => {
        const patchObject = Object.assign(
          {
            customerPays: 1,
            taxiGets: 1,
            created: currentDate.format(DATE_TIME_FORMAT),
          },
          new TaximeterOfferItem()
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

      it('should return a list of TaximeterOfferItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            billCompanyPays: 1,
            customerPays: 1,
            taxiGets: 1,
            taxiPays: 1,
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

      it('should delete a TaximeterOfferItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterOfferItemToCollectionIfMissing', () => {
        it('should add a TaximeterOfferItem to an empty array', () => {
          const taximeterOfferItem: ITaximeterOfferItem = { id: 123 };
          expectedResult = service.addTaximeterOfferItemToCollectionIfMissing([], taximeterOfferItem);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterOfferItem);
        });

        it('should not add a TaximeterOfferItem to an array that contains it', () => {
          const taximeterOfferItem: ITaximeterOfferItem = { id: 123 };
          const taximeterOfferItemCollection: ITaximeterOfferItem[] = [
            {
              ...taximeterOfferItem,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterOfferItemToCollectionIfMissing(taximeterOfferItemCollection, taximeterOfferItem);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterOfferItem to an array that doesn't contain it", () => {
          const taximeterOfferItem: ITaximeterOfferItem = { id: 123 };
          const taximeterOfferItemCollection: ITaximeterOfferItem[] = [{ id: 456 }];
          expectedResult = service.addTaximeterOfferItemToCollectionIfMissing(taximeterOfferItemCollection, taximeterOfferItem);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterOfferItem);
        });

        it('should add only unique TaximeterOfferItem to an array', () => {
          const taximeterOfferItemArray: ITaximeterOfferItem[] = [{ id: 123 }, { id: 456 }, { id: 74565 }];
          const taximeterOfferItemCollection: ITaximeterOfferItem[] = [{ id: 123 }];
          expectedResult = service.addTaximeterOfferItemToCollectionIfMissing(taximeterOfferItemCollection, ...taximeterOfferItemArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterOfferItem: ITaximeterOfferItem = { id: 123 };
          const taximeterOfferItem2: ITaximeterOfferItem = { id: 456 };
          expectedResult = service.addTaximeterOfferItemToCollectionIfMissing([], taximeterOfferItem, taximeterOfferItem2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterOfferItem);
          expect(expectedResult).toContain(taximeterOfferItem2);
        });

        it('should accept null and undefined values', () => {
          const taximeterOfferItem: ITaximeterOfferItem = { id: 123 };
          expectedResult = service.addTaximeterOfferItemToCollectionIfMissing([], null, taximeterOfferItem, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterOfferItem);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
