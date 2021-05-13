import * as dayjs from 'dayjs';
import { ITaximeterTimeRangeItem } from 'app/entities/taximeter-time-range-item/taximeter-time-range-item.model';

export interface ITaximeterTimeRange {
  id?: number;
  name?: string;
  description?: string | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  items?: ITaximeterTimeRangeItem[] | null;
}

export class TaximeterTimeRange implements ITaximeterTimeRange {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null,
    public items?: ITaximeterTimeRangeItem[] | null
  ) {}
}

export function getTaximeterTimeRangeIdentifier(taximeterTimeRange: ITaximeterTimeRange): number | undefined {
  return taximeterTimeRange.id;
}
