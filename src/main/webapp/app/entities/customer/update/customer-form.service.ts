import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICustomer, NewCustomer } from '../customer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICustomer for edit and NewCustomerFormGroupInput for create.
 */
type CustomerFormGroupInput = ICustomer | PartialWithRequiredKeyOf<NewCustomer>;

type CustomerFormDefaults = Pick<NewCustomer, 'id' | 'roles'>;

type CustomerFormGroupContent = {
  id: FormControl<ICustomer['id'] | NewCustomer['id']>;
  active: FormControl<ICustomer['active']>;
  customerName: FormControl<ICustomer['customerName']>;
  email: FormControl<ICustomer['email']>;
  password: FormControl<ICustomer['password']>;
  recordWpm: FormControl<ICustomer['recordWpm']>;
  photo: FormControl<ICustomer['photo']>;
  createdAt: FormControl<ICustomer['createdAt']>;
  group: FormControl<ICustomer['group']>;
  roles: FormControl<ICustomer['roles']>;
};

export type CustomerFormGroup = FormGroup<CustomerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CustomerFormService {
  createCustomerFormGroup(customer: CustomerFormGroupInput = { id: null }): CustomerFormGroup {
    const customerRawValue = {
      ...this.getFormDefaults(),
      ...customer,
    };
    return new FormGroup<CustomerFormGroupContent>({
      id: new FormControl(
        { value: customerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      active: new FormControl(customerRawValue.active),
      customerName: new FormControl(customerRawValue.customerName, {
        validators: [Validators.required],
      }),
      email: new FormControl(customerRawValue.email, {
        validators: [Validators.required],
      }),
      password: new FormControl(customerRawValue.password, {
        validators: [Validators.required],
      }),
      recordWpm: new FormControl(customerRawValue.recordWpm),
      photo: new FormControl(customerRawValue.photo),
      createdAt: new FormControl(customerRawValue.createdAt),
      group: new FormControl(customerRawValue.group),
      roles: new FormControl(customerRawValue.roles ?? []),
    });
  }

  getCustomer(form: CustomerFormGroup): ICustomer | NewCustomer {
    return form.getRawValue() as ICustomer | NewCustomer;
  }

  resetForm(form: CustomerFormGroup, customer: CustomerFormGroupInput): void {
    const customerRawValue = { ...this.getFormDefaults(), ...customer };
    form.reset(
      {
        ...customerRawValue,
        id: { value: customerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CustomerFormDefaults {
    return {
      id: null,
      roles: [],
    };
  }
}
