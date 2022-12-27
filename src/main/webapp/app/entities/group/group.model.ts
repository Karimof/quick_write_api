export interface IGroup {
  id: number;
  name?: string | null;
  password?: string | null;
}

export type NewGroup = Omit<IGroup, 'id'> & { id: null };
