import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFormulaRow } from '../taximeter-formula-row.model';
import { TaximeterFormulaRowService } from '../service/taximeter-formula-row.service';

@Component({
  templateUrl: './taximeter-formula-row-delete-dialog.component.html',
})
export class TaximeterFormulaRowDeleteDialogComponent {
  taximeterFormulaRow?: ITaximeterFormulaRow;

  constructor(protected taximeterFormulaRowService: TaximeterFormulaRowService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterFormulaRowService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
