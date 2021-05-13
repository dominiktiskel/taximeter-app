import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterOffer } from '../taximeter-offer.model';
import { TaximeterOfferService } from '../service/taximeter-offer.service';

@Component({
  templateUrl: './taximeter-offer-delete-dialog.component.html',
})
export class TaximeterOfferDeleteDialogComponent {
  taximeterOffer?: ITaximeterOffer;

  constructor(protected taximeterOfferService: TaximeterOfferService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterOfferService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
