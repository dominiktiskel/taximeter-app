import * as dayjs from 'dayjs';
import { ITaximeterTimeRange } from 'app/entities/taximeter-time-range/taximeter-time-range.model';

export interface ITaximeterTimeRangeItem {
  id?: number;
  day?: string;
  hours?: string;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  range?: ITaximeterTimeRange;
}

export class TaximeterTimeRangeItem implements ITaximeterTimeRangeItem {
  constructor(
    public id?: number,
    public day?: string,
    public hours?: string,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null,
    public range?: ITaximeterTimeRange
  ) {}
}

export function getTaximeterTimeRangeItemIdentifier(taximeterTimeRangeItem: ITaximeterTimeRangeItem): number | undefined {
  return taximeterTimeRangeItem.id;
}
