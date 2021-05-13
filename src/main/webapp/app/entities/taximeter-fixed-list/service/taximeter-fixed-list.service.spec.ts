import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TaximeterFixedListType } from 'app/entities/enumerations/taximeter-fixed-list-type.model';
import { ITaximeterFixedList, TaximeterFixedList } from '../taximeter-fixed-list.model';

import { TaximeterFixedListService } from './taximeter-fixed-list.service';

describe('Service Tests', () => {
  describe('TaximeterFixedList Service', () => {
    let service: TaximeterFixedListService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaximeterFixedList;
    let expectedResult: ITaximeterFixedList | ITaximeterFixedList[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaximeterFixedListService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        type: TaximeterFixedListType.POST_CODE,
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

      it('should create a TaximeterFixedList', () => {
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

        service.create(new TaximeterFixedList()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaximeterFixedList', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            type: 'BBBBBB',
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

      it('should partial update a TaximeterFixedList', () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            type: 'BBBBBB',
            created: currentDate.format(DATE_TIME_FORMAT),
            updated: currentDate.format(DATE_TIME_FORMAT),
          },
          new TaximeterFixedList()
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

      it('should return a list of TaximeterFixedList', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            type: 'BBBBBB',
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

      it('should delete a TaximeterFixedList', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaximeterFixedListToCollectionIfMissing', () => {
        it('should add a TaximeterFixedList to an empty array', () => {
          const taximeterFixedList: ITaximeterFixedList = { id: 123 };
          expectedResult = service.addTaximeterFixedListToCollectionIfMissing([], taximeterFixedList);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFixedList);
        });

        it('should not add a TaximeterFixedList to an array that contains it', () => {
          const taximeterFixedList: ITaximeterFixedList = { id: 123 };
          const taximeterFixedListCollection: ITaximeterFixedList[] = [
            {
              ...taximeterFixedList,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaximeterFixedListToCollectionIfMissing(taximeterFixedListCollection, taximeterFixedList);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaximeterFixedList to an array that doesn't contain it", () => {
          const taximeterFixedList: ITaximeterFixedList = { id: 123 };
          const taximeterFixedListCollection: ITaximeterFixedList[] = [{ id: 456 }];
          expectedResult = service.addTaximeterFixedListToCollectionIfMissing(taximeterFixedListCollection, taximeterFixedList);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFixedList);
        });

        it('should add only unique TaximeterFixedList to an array', () => {
          const taximeterFixedListArray: ITaximeterFixedList[] = [{ id: 123 }, { id: 456 }, { id: 69019 }];
          const taximeterFixedListCollection: ITaximeterFixedList[] = [{ id: 123 }];
          expectedResult = service.addTaximeterFixedListToCollectionIfMissing(taximeterFixedListCollection, ...taximeterFixedListArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taximeterFixedList: ITaximeterFixedList = { id: 123 };
          const taximeterFixedList2: ITaximeterFixedList = { id: 456 };
          expectedResult = service.addTaximeterFixedListToCollectionIfMissing([], taximeterFixedList, taximeterFixedList2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taximeterFixedList);
          expect(expectedResult).toContain(taximeterFixedList2);
        });

        it('should accept null and undefined values', () => {
          const taximeterFixedList: ITaximeterFixedList = { id: 123 };
          expectedResult = service.addTaximeterFixedListToCollectionIfMissing([], null, taximeterFixedList, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taximeterFixedList);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
