import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IText, NewText } from '../text.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IText for edit and NewTextFormGroupInput for create.
 */
type TextFormGroupInput = IText | PartialWithRequiredKeyOf<NewText>;

type TextFormDefaults = Pick<NewText, 'id'>;

type TextFormGroupContent = {
  id: FormControl<IText['id'] | NewText['id']>;
  text: FormControl<IText['text']>;
};

export type TextFormGroup = FormGroup<TextFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TextFormService {
  createTextFormGroup(text: TextFormGroupInput = { id: null }): TextFormGroup {
    const textRawValue = {
      ...this.getFormDefaults(),
      ...text,
    };
    return new FormGroup<TextFormGroupContent>({
      id: new FormControl(
        { value: textRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      text: new FormControl(textRawValue.text, {
        validators: [Validators.required],
      }),
    });
  }

  getText(form: TextFormGroup): IText | NewText {
    return form.getRawValue() as IText | NewText;
  }

  resetForm(form: TextFormGroup, text: TextFormGroupInput): void {
    const textRawValue = { ...this.getFormDefaults(), ...text };
    form.reset(
      {
        ...textRawValue,
        id: { value: textRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TextFormDefaults {
    return {
      id: null,
    };
  }
}
