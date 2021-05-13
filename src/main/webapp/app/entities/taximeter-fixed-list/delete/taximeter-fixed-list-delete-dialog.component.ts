import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFixedList } from '../taximeter-fixed-list.model';
import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';

@Component({
  templateUrl: './taximeter-fixed-list-delete-dialog.component.html',
})
export class TaximeterFixedListDeleteDialogComponent {
  taximeterFixedList?: ITaximeterFixedList;

  constructor(protected taximeterFixedListService: TaximeterFixedListService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taximeterFixedListService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
