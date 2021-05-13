import * as dayjs from 'dayjs';
import { TaximeterFormulaType } from 'app/entities/enumerations/taximeter-formula-type.model';
import { TaximeterChargeByType } from 'app/entities/enumerations/taximeter-charge-by-type.model';

export interface ITaximeterFormula {
  id?: number;
  name?: string;
  description?: string | null;
  type?: TaximeterFormulaType;
  chargeBy?: TaximeterChargeByType;
  jsonData?: string;
  created?: dayjs.Dayjs | null;
  updated?: dayjs.Dayjs | null;
}

export class TaximeterFormula implements ITaximeterFormula {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public type?: TaximeterFormulaType,
    public chargeBy?: TaximeterChargeByType,
    public jsonData?: string,
    public created?: dayjs.Dayjs | null,
    public updated?: dayjs.Dayjs | null
  ) {}
}

export function getTaximeterFormulaIdentifier(taximeterFormula: ITaximeterFormula): number | undefined {
  return taximeterFormula.id;
}
