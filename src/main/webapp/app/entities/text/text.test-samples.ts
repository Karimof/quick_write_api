import { IText, NewText } from './text.model';

export const sampleWithRequiredData: IText = {
  id: 16082,
  text: 'Data',
};

export const sampleWithPartialData: IText = {
  id: 10348,
  text: 'Gorgeous SDD feed',
};

export const sampleWithFullData: IText = {
  id: 36360,
  text: 'overriding Administrator transform',
};

export const sampleWithNewData: NewText = {
  text: 'Liaison pink SDD',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
