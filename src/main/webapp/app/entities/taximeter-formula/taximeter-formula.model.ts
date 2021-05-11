import { ITaximeterFormulaRow } from 'app/entities/taximeter-formula-row/taximeter-formula-row.model';
import { TaximeterFormulaType } from 'app/entities/enumerations/taximeter-formula-type.model';

export interface ITaximeterFormula {
  id?: number;
  type?: TaximeterFormulaType;
  name?: string;
  active?: boolean;
  taximeterFormulaRows?: ITaximeterFormulaRow[] | null;
}

export class TaximeterFormula implements ITaximeterFormula {
  constructor(
    public id?: number,
    public type?: TaximeterFormulaType,
    public name?: string,
    public active?: boolean,
    public taximeterFormulaRows?: ITaximeterFormulaRow[] | null
  ) {
    this.active = this.active ?? false;
  }
}

export function getTaximeterFormulaIdentifier(taximeterFormula: ITaximeterFormula): number | undefined {
  return taximeterFormula.id;
}
