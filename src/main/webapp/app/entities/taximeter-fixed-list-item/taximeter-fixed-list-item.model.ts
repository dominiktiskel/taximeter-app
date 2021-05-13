import * as dayjs from 'dayjs';
import { ITaximeterFixedList } from 'app/entities/taximeter-fixed-list/taximeter-fixed-list.model';

export interface ITaximeterFixedListItem {
  id?: number;
  from?: string;
  to?: string;
  value?: number;
  valueReverse?: number | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  list?: ITaximeterFixedList;
}

export class TaximeterFixedListItem implements ITaximeterFixedListItem {
  constructor(
    public id?: number,
    public from?: string,
    public to?: string,
    public value?: number,
    public valueReverse?: number | null,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null,
    public list?: ITaximeterFixedList
  ) {}
}

export function getTaximeterFixedListItemIdentifier(taximeterFixedListItem: ITaximeterFixedListItem): number | undefined {
  return taximeterFixedListItem.id;
}
