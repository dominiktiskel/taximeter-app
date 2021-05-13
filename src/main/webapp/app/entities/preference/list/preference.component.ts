import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPreference } from '../preference.model';
import { PreferenceService } from '../service/preference.service';
import { PreferenceDeleteDialogComponent } from '../delete/preference-delete-dialog.component';

@Component({
  selector: 'jhi-preference',
  templateUrl: './preference.component.html',
})
export class PreferenceComponent implements OnInit {
  preferences?: IPreference[];
  isLoading = false;

  constructor(protected preferenceService: PreferenceService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.preferenceService.query().subscribe(
      (res: HttpResponse<IPreference[]>) => {
        this.isLoading = false;
        this.preferences = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPreference): number {
    return item.id!;
  }

  delete(preference: IPreference): void {
    const modalRef = this.modalService.open(PreferenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.preference = preference;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
