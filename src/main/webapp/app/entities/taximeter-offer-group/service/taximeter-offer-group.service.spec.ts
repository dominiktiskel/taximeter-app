import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TaximeterChargeByType } from 'app/entities/enumerations/taximeter-charge-by-type.model';
import { ITaximeterOfferGroup, TaximeterOfferGroup } from '../taximeter-offer-group.model';

import { TaximeterOfferGroupService } from './taximeter-offer-group.service';

describe('Service Tests', () => {
  describe('TaximeterOfferGroup Service', () => {
    let service: TaximeterOfferGroupService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterOfferGroup;
    let expectedResult: ITaximeterOfferGroup | ITaximeterOfferGroup[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterOfferGroupService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        invoiceAs: 'AAAAAAA',
        chargeBy: TaximeterChargeByType.DISTANCE,
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

      it('should create a TaximeterOfferGroup', () => {
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

        service.create(new TaximeterOfferGroup()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterOfferGroup', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            invoiceAs: 'BBBBBB',
            chargeBy: 'BBBBBB',
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

      it('should partial update a TaximeterOfferGroup', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            invoiceAs: 'BBBBBB',
            chargeBy: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          new TaximeterOfferGroup()
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

      it('should return a list of TaximeterOfferGroup', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            invoiceAs: 'BBBBBB',
            chargeBy: 'BBBBBB',
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

      it('should delete a TaximeterOfferGroup', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterOfferGroupToCollectionIfMissing', () => {
        it('should add a TaximeterOfferGroup to an empty array', () => {
          const taximeterOfferGroup: ITaximeterOfferGroup = { id: 123 };
          expectedResult = service.addTaximeterOfferGroupToCollectionIfMissing([], taximeterOfferGroup);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterOfferGroup);
        });

        it('should not add a TaximeterOfferGroup to an array that contains it', () => {
          const taximeterOfferGroup: ITaximeterOfferGroup = { id: 123 };
          const taximeterOfferGroupCollection: ITaximeterOfferGroup[] = [
            {
              ...taximeterOfferGroup,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterOfferGroupToCollectionIfMissing(taximeterOfferGroupCollection, taximeterOfferGroup);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterOfferGroup to an array that doesn't contain it", () => {
          const taximeterOfferGroup: ITaximeterOfferGroup = { id: 123 };
          const taximeterOfferGroupCollection: ITaximeterOfferGroup[] = [{ id: 456 }];
          expectedResult = service.addTaximeterOfferGroupToCollectionIfMissing(taximeterOfferGroupCollection, taximeterOfferGroup);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterOfferGroup);
        });

        it('should add only unique TaximeterOfferGroup to an array', () => {
          const taximeterOfferGroupArray: ITaximeterOfferGroup[] = [{ id: 123 }, { id: 456 }, { id: 89016 }];
          const taximeterOfferGroupCollection: ITaximeterOfferGroup[] = [{ id: 123 }];
          expectedResult = service.addTaximeterOfferGroupToCollectionIfMissing(taximeterOfferGroupCollection, ...taximeterOfferGroupArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterOfferGroup: ITaximeterOfferGroup = { id: 123 };
          const taximeterOfferGroup2: ITaximeterOfferGroup = { id: 456 };
          expectedResult = service.addTaximeterOfferGroupToCollectionIfMissing([], taximeterOfferGroup, taximeterOfferGroup2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterOfferGroup);
          expect(expectedResult).toContain(taximeterOfferGroup2);
        });

        it('should accept null and undefined values', () => {
          const taximeterOfferGroup: ITaximeterOfferGroup = { id: 123 };
          expectedResult = service.addTaximeterOfferGroupToCollectionIfMissing([], null, taximeterOfferGroup, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterOfferGroup);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
