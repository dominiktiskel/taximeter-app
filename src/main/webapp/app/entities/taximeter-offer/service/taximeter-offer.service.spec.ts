import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITaximeterOffer, TaximeterOffer } from '../taximeter-offer.model';

import { TaximeterOfferService } from './taximeter-offer.service';

describe('Service Tests', () => {
  describe('TaximeterOffer Service', () => {
    let service: TaximeterOfferService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterOffer;
    let expectedResult: ITaximeterOffer | ITaximeterOffer[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterOfferService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        validFrom: currentDate,
        validTo: currentDate,
        created: currentDate,
        updated: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a TaximeterOffer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
            created: currentDate,
            updated: currentDate,
          },
          returnedFromService
        );

        service.create(new TaximeterOffer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterOffer', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
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

      it('should partial update a TaximeterOffer', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            validTo: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          new TaximeterOffer()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
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

      it('should return a list of TaximeterOffer', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT),
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate,
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

      it('should delete a TaximeterOffer', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterOfferToCollectionIfMissing', () => {
        it('should add a TaximeterOffer to an empty array', () => {
          const taximeterOffer: ITaximeterOffer = { id: 123 };
          expectedResult = service.addTaximeterOfferToCollectionIfMissing([], taximeterOffer);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterOffer);
        });

        it('should not add a TaximeterOffer to an array that contains it', () => {
          const taximeterOffer: ITaximeterOffer = { id: 123 };
          const taximeterOfferCollection: ITaximeterOffer[] = [
            {
              ...taximeterOffer,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterOfferToCollectionIfMissing(taximeterOfferCollection, taximeterOffer);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterOffer to an array that doesn't contain it", () => {
          const taximeterOffer: ITaximeterOffer = { id: 123 };
          const taximeterOfferCollection: ITaximeterOffer[] = [{ id: 456 }];
          expectedResult = service.addTaximeterOfferToCollectionIfMissing(taximeterOfferCollection, taximeterOffer);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterOffer);
        });

        it('should add only unique TaximeterOffer to an array', () => {
          const taximeterOfferArray: ITaximeterOffer[] = [{ id: 123 }, { id: 456 }, { id: 37201 }];
          const taximeterOfferCollection: ITaximeterOffer[] = [{ id: 123 }];
          expectedResult = service.addTaximeterOfferToCollectionIfMissing(taximeterOfferCollection, ...taximeterOfferArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterOffer: ITaximeterOffer = { id: 123 };
          const taximeterOffer2: ITaximeterOffer = { id: 456 };
          expectedResult = service.addTaximeterOfferToCollectionIfMissing([], taximeterOffer, taximeterOffer2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterOffer);
          expect(expectedResult).toContain(taximeterOffer2);
        });

        it('should accept null and undefined values', () => {
          const taximeterOffer: ITaximeterOffer = { id: 123 };
          expectedResult = service.addTaximeterOfferToCollectionIfMissing([], null, taximeterOffer, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterOffer);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
