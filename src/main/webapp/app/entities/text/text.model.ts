export interface IText {
  id: number;
  text?: string | null;
}

export type NewText = Omit<IText, 'id'> & { id: null };
