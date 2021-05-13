import * as dayjs from 'dayjs';
import { ITaximeterFixedList } from 'app/entities/taximeter-fixed-list/taximeter-fixed-list.model';
import { ITaximeterFormula } from 'app/entities/taximeter-formula/taximeter-formula.model';
import { ITaximeterTimeRange } from 'app/entities/taximeter-time-range/taximeter-time-range.model';
import { IPreference } from 'app/entities/preference/preference.model';
import { ITaximeterOfferGroup } from 'app/entities/taximeter-offer-group/taximeter-offer-group.model';

export interface ITaximeterOfferItem {
  id?: number;
  name?: string;
  description?: string | null;
  billCompanyPays?: number | null;
  customerPays?: number | null;
  taxiGets?: number | null;
  taxiPays?: number | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  fixedList1?: ITaximeterFixedList | null;
  fixedList2?: ITaximeterFixedList | null;
  formula?: ITaximeterFormula | null;
  timeRange?: ITaximeterTimeRange | null;
  preferences?: IPreference[] | null;
  group?: ITaximeterOfferGroup;
}

export class TaximeterOfferItem implements ITaximeterOfferItem {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public billCompanyPays?: number | null,
    public customerPays?: number | null,
    public taxiGets?: number | null,
    public taxiPays?: number | null,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null,
    public fixedList1?: ITaximeterFixedList | null,
    public fixedList2?: ITaximeterFixedList | null,
    public formula?: ITaximeterFormula | null,
    public timeRange?: ITaximeterTimeRange | null,
    public preferences?: IPreference[] | null,
    public group?: ITaximeterOfferGroup
  ) {}
}

export function getTaximeterOfferItemIdentifier(taximeterOfferItem: ITaximeterOfferItem): number | undefined {
  return taximeterOfferItem.id;
}
