import dayjs from 'dayjs/esm';

import { ICustomer, NewCustomer } from './customer.model';

export const sampleWithRequiredData: ICustomer = {
  id: 24379,
  customerName: 'Operations',
  email: 'Vaughn.Mosciski8@yahoo.com',
  password: '1080p',
};

export const sampleWithPartialData: ICustomer = {
  id: 85053,
  active: 'analyzer invoice',
  customerName: 'Cotton payment',
  email: 'Eloise_Runte65@gmail.com',
  password: 'Montana Maine',
  recordWpm: 78864,
  photo: 'intangible Balanced',
  createdAt: dayjs('2022-12-25'),
};

export const sampleWithFullData: ICustomer = {
  id: 30051,
  active: 'Maine',
  customerName: 'silver transmitter',
  email: 'Antoinette53@gmail.com',
  password: 'Soft',
  recordWpm: 51758,
  photo: 'calculate up',
  createdAt: dayjs('2022-12-25'),
};

export const sampleWithNewData: NewCustomer = {
  customerName: 'Loan Pants throughput',
  email: 'Bernita18@gmail.com',
  password: 'Branding Multi-channelled Steel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
