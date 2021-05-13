import * as dayjs from 'dayjs';
import { ITaximeterFixedListItem } from 'app/entities/taximeter-fixed-list-item/taximeter-fixed-list-item.model';
import { TaximeterFixedListType } from 'app/entities/enumerations/taximeter-fixed-list-type.model';
import { TaximeterMultipickupMatchingRules } from 'app/entities/enumerations/taximeter-multipickup-matching-rules.model';

export interface ITaximeterFixedList {
  id?: number;
  name?: string;
  description?: string | null;
  type?: TaximeterFixedListType;
  matchingRules?: TaximeterMultipickupMatchingRules | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  items?: ITaximeterFixedListItem[] | null;
}

export class TaximeterFixedList implements ITaximeterFixedList {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public type?: TaximeterFixedListType,
    public matchingRules?: TaximeterMultipickupMatchingRules | null,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null,
    public items?: ITaximeterFixedListItem[] | null
  ) {}
}

export function getTaximeterFixedListIdentifier(taximeterFixedList: ITaximeterFixedList): number | undefined {
  return taximeterFixedList.id;
}
