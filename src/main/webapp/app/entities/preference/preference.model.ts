import { ITaximeterOfferItem } from 'app/entities/taximeter-offer-item/taximeter-offer-item.model';

export interface IPreference {
  id?: number;
  taximeterOfferItems?: ITaximeterOfferItem[] | null;
}

export class Preference implements IPreference {
  constructor(public id?: number, public taximeterOfferItems?: ITaximeterOfferItem[] | null) {}
}

export function getPreferenceIdentifier(preference: IPreference): number | undefined {
  return preference.id;
}
