import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFormula } from '../taximeter-formula.model';
import { TaximeterFormulaService } from '../service/taximeter-formula.service';

@Component({
  templateUrl: './taximeter-formula-delete-dialog.component.html',
})
export class TaximeterFormulaDeleteDialogComponent {
  taximeterFormula?: ITaximeterFormula;

  constructor(protected taximeterFormulaService: TaximeterFormulaService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterFormulaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
