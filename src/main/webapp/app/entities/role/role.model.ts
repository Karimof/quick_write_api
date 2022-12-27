import { ICustomer } from 'app/entities/customer/customer.model';

export interface IRole {
  id: number;
  name?: string | null;
  customers?: Pick<ICustomer, 'id'>[] | null;
}

export type NewRole = Omit<IRole, 'id'> & { id: null };
