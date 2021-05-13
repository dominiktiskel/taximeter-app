import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPreference, Preference } from '../preference.model';

import { PreferenceService } from './preference.service';

describe('Service Tests', () => {
  describe('Preference Service', () => {
    let service: PreferenceService;
    let httpMock: HttpTestingController;
    let elemDefault: IPreference;
    let expectedResult: IPreference | IPreference[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PreferenceService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a Preference', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Preference()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Preference', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Preference', () => {
        const patchObject = Object.assign({}, new Preference());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Preference', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a Preference', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPreferenceToCollectionIfMissing', () => {
        it('should add a Preference to an empty array', () => {
          const preference: IPreference = { id: 123 };
          expectedResult = service.addPreferenceToCollectionIfMissing([], preference);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(preference);
        });

        it('should not add a Preference to an array that contains it', () => {
          const preference: IPreference = { id: 123 };
          const preferenceCollection: IPreference[] = [
            {
              ...preference,
            },
            { id: 456 },
          ];
          expectedResult = service.addPreferenceToCollectionIfMissing(preferenceCollection, preference);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Preference to an array that doesn't contain it", () => {
          const preference: IPreference = { id: 123 };
          const preferenceCollection: IPreference[] = [{ id: 456 }];
          expectedResult = service.addPreferenceToCollectionIfMissing(preferenceCollection, preference);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(preference);
        });

        it('should add only unique Preference to an array', () => {
          const preferenceArray: IPreference[] = [{ id: 123 }, { id: 456 }, { id: 42780 }];
          const preferenceCollection: IPreference[] = [{ id: 123 }];
          expectedResult = service.addPreferenceToCollectionIfMissing(preferenceCollection, ...preferenceArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const preference: IPreference = { id: 123 };
          const preference2: IPreference = { id: 456 };
          expectedResult = service.addPreferenceToCollectionIfMissing([], preference, preference2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(preference);
          expect(expectedResult).toContain(preference2);
        });

        it('should accept null and undefined values', () => {
          const preference: IPreference = { id: 123 };
          expectedResult = service.addPreferenceToCollectionIfMissing([], null, preference, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(preference);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
