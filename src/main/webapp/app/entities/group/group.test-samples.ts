import { IGroup, NewGroup } from './group.model';

export const sampleWithRequiredData: IGroup = {
  id: 82913,
  name: 'interactive',
  password: 'Account Infrastructure withdrawal',
};

export const sampleWithPartialData: IGroup = {
  id: 51736,
  name: 'Helena Personal transmitter',
  password: 'Refined methodologies',
};

export const sampleWithFullData: IGroup = {
  id: 62856,
  name: 'synthesizing',
  password: 'Auto Tasty',
};

export const sampleWithNewData: NewGroup = {
  name: 'Cotton Movies',
  password: 'Account iterate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
