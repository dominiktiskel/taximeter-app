import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPreference } from '../preference.model';
import { PreferenceService } from '../service/preference.service';

@Component({
  templateUrl: './preference-delete-dialog.component.html',
})
export class PreferenceDeleteDialogComponent {
  preference?: IPreference;

  constructor(protected preferenceService: PreferenceService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.preferenceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
