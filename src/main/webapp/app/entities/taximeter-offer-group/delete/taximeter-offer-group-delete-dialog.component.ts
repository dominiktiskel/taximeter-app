import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterOfferGroup } from '../taximeter-offer-group.model';
import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';

@Component({
  templateUrl: './taximeter-offer-group-delete-dialog.component.html',
})
export class TaximeterOfferGroupDeleteDialogComponent {
  taximeterOfferGroup?: ITaximeterOfferGroup;

  constructor(protected taximeterOfferGroupService: TaximeterOfferGroupService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterOfferGroupService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
