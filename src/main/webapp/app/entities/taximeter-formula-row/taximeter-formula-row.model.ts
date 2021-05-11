import { ITaximeterFormula } from 'app/entities/taximeter-formula/taximeter-formula.model';
import { TaximeterFormulaRowType } from 'app/entities/enumerations/taximeter-formula-row-type.model';

export interface ITaximeterFormulaRow {
  id?: number;
  type?: TaximeterFormulaRowType;
  value?: number;
  step?: number;
  granulation?: number | null;
  row?: ITaximeterFormula | null;
}

export class TaximeterFormulaRow implements ITaximeterFormulaRow {
  constructor(
    public id?: number,
    public type?: TaximeterFormulaRowType,
    public value?: number,
    public step?: number,
    public granulation?: number | null,
    public row?: ITaximeterFormula | null
  ) {}
}

export function getTaximeterFormulaRowIdentifier(taximeterFormulaRow: ITaximeterFormulaRow): number | undefined {
  return taximeterFormulaRow.id;
}
