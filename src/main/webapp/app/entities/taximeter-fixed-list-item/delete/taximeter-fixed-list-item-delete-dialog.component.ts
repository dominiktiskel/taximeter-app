import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFixedListItem } from '../taximeter-fixed-list-item.model';
import { TaximeterFixedListItemService } from '../service/taximeter-fixed-list-item.service';

@Component({
  templateUrl: './taximeter-fixed-list-item-delete-dialog.component.html',
})
export class TaximeterFixedListItemDeleteDialogComponent {
  taximeterFixedListItem?: ITaximeterFixedListItem;

  constructor(protected taximeterFixedListItemService: TaximeterFixedListItemService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterFixedListItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
