import * as dayjs from 'dayjs';
import { ITaximeterOfferItem } from 'app/entities/taximeter-offer-item/taximeter-offer-item.model';
import { ITaximeterOffer } from 'app/entities/taximeter-offer/taximeter-offer.model';
import { TaximeterChargeByType } from 'app/entities/enumerations/taximeter-charge-by-type.model';

export interface ITaximeterOfferGroup {
  id?: number;
  name?: string;
  description?: string | null;
  invoiceAs?: string | null;
  chargeBy?: TaximeterChargeByType;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
  items?: ITaximeterOfferItem[] | null;
  offer?: ITaximeterOffer;
}

export class TaximeterOfferGroup implements ITaximeterOfferGroup {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public invoiceAs?: string | null,
    public chargeBy?: TaximeterChargeByType,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null,
    public items?: ITaximeterOfferItem[] | null,
    public offer?: ITaximeterOffer
  ) {}
}

export function getTaximeterOfferGroupIdentifier(taximeterOfferGroup: ITaximeterOfferGroup): number | undefined {
  return taximeterOfferGroup.id;
}
