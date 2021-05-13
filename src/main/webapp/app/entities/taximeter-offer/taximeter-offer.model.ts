import * as dayjs from 'dayjs';
import { ITaximeterOfferGroup } from 'app/entities/taximeter-offer-group/taximeter-offer-group.model';

export interface ITaximeterOffer {
  id?: number;
  name?: string;
  description?: string | null;
  validFrom?: dayjs.Dayjs | null;
  validTo?: dayjs.Dayjs | null;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  groups?: ITaximeterOfferGroup[] | null;
}

export class TaximeterOffer implements ITaximeterOffer {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public validFrom?: dayjs.Dayjs | null,
    public validTo?: dayjs.Dayjs | null,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null,
    public groups?: ITaximeterOfferGroup[] | null
  ) {}
}

export function getTaximeterOfferIdentifier(taximeterOffer: ITaximeterOffer): number | undefined {
  return taximeterOffer.id;
}
