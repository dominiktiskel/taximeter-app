import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterTimeRangeItem } from '../taximeter-time-range-item.model';
import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';

@Component({
  templateUrl: './taximeter-time-range-item-delete-dialog.component.html',
})
export class TaximeterTimeRangeItemDeleteDialogComponent {
  taximeterTimeRangeItem?: ITaximeterTimeRangeItem;

  constructor(protected taximeterTimeRangeItemService: TaximeterTimeRangeItemService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterTimeRangeItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
