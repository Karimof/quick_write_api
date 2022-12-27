import dayjs from 'dayjs/esm';
import { IGroup } from 'app/entities/group/group.model';
import { IRole } from 'app/entities/role/role.model';

export interface ICustomer {
  id: number;
  active?: string | null;
  customerName?: string | null;
  email?: string | null;
  password?: string | null;
  recordWpm?: number | null;
  photo?: string | null;
  createdAt?: dayjs.Dayjs | null;
  group?: Pick<IGroup, 'id'> | null;
  roles?: Pick<IRole, 'id'>[] | null;
}

export type NewCustomer = Omit<ICustomer, 'id'> & { id: null };
