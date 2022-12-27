import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../text.test-samples';

import { TextFormService } from './text-form.service';

describe('Text Form Service', () => {
  let service: TextFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TextFormService);
  });

  describe('Service methods', () => {
    describe('createTextFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTextFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            text: expect.any(Object),
          })
        );
      });

      it('passing IText should create a new form with FormGroup', () => {
        const formGroup = service.createTextFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            text: expect.any(Object),
          })
        );
      });
    });

    describe('getText', () => {
      it('should return NewText for default Text initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTextFormGroup(sampleWithNewData);

        const text = service.getText(formGroup) as any;

        expect(text).toMatchObject(sampleWithNewData);
      });

      it('should return NewText for empty Text initial value', () => {
        const formGroup = service.createTextFormGroup();

        const text = service.getText(formGroup) as any;

        expect(text).toMatchObject({});
      });

      it('should return IText', () => {
        const formGroup = service.createTextFormGroup(sampleWithRequiredData);

        const text = service.getText(formGroup) as any;

        expect(text).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IText should not enable id FormControl', () => {
        const formGroup = service.createTextFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewText should disable id FormControl', () => {
        const formGroup = service.createTextFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
