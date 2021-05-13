import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterOfferItem } from '../taximeter-offer-item.model';
import { TaximeterOfferItemService } from '../service/taximeter-offer-item.service';

@Component({
  templateUrl: './taximeter-offer-item-delete-dialog.component.html',
})
export class TaximeterOfferItemDeleteDialogComponent {
  taximeterOfferItem?: ITaximeterOfferItem;

  constructor(protected taximeterOfferItemService: TaximeterOfferItemService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterOfferItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
