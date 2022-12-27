import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IText } from '../text.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../text.test-samples';

import { TextService } from './text.service';

const requireRestSample: IText = {
  ...sampleWithRequiredData,
};

describe('Text Service', () => {
  let service: TextService;
  let httpMock: HttpTestingController;
  let expectedResult: IText | IText[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TextService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Text', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const text = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(text).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Text', () => {
      const text = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(text).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Text', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Text', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Text', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTextToCollectionIfMissing', () => {
      it('should add a Text to an empty array', () => {
        const text: IText = sampleWithRequiredData;
        expectedResult = service.addTextToCollectionIfMissing([], text);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(text);
      });

      it('should not add a Text to an array that contains it', () => {
        const text: IText = sampleWithRequiredData;
        const textCollection: IText[] = [
          {
            ...text,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTextToCollectionIfMissing(textCollection, text);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Text to an array that doesn't contain it", () => {
        const text: IText = sampleWithRequiredData;
        const textCollection: IText[] = [sampleWithPartialData];
        expectedResult = service.addTextToCollectionIfMissing(textCollection, text);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(text);
      });

      it('should add only unique Text to an array', () => {
        const textArray: IText[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const textCollection: IText[] = [sampleWithRequiredData];
        expectedResult = service.addTextToCollectionIfMissing(textCollection, ...textArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const text: IText = sampleWithRequiredData;
        const text2: IText = sampleWithPartialData;
        expectedResult = service.addTextToCollectionIfMissing([], text, text2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(text);
        expect(expectedResult).toContain(text2);
      });

      it('should accept null and undefined values', () => {
        const text: IText = sampleWithRequiredData;
        expectedResult = service.addTextToCollectionIfMissing([], null, text, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(text);
      });

      it('should return initial array if no Text is added', () => {
        const textCollection: IText[] = [sampleWithRequiredData];
        expectedResult = service.addTextToCollectionIfMissing(textCollection, undefined, null);
        expect(expectedResult).toEqual(textCollection);
      });
    });

    describe('compareText', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareText(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareText(entity1, entity2);
        const compareResult2 = service.compareText(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareText(entity1, entity2);
        const compareResult2 = service.compareText(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareText(entity1, entity2);
        const compareResult2 = service.compareText(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
