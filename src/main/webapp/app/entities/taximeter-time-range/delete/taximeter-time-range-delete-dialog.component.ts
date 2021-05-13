import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterTimeRange } from '../taximeter-time-range.model';
import { TaximeterTimeRangeService } from '../service/taximeter-time-range.service';

@Component({
  templateUrl: './taximeter-time-range-delete-dialog.component.html',
})
export class TaximeterTimeRangeDeleteDialogComponent {
  taximeterTimeRange?: ITaximeterTimeRange;

  constructor(protected taximeterTimeRangeService: TaximeterTimeRangeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterTimeRangeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
