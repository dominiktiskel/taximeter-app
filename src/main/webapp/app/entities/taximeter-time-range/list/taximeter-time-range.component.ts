import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterTimeRange } from '../taximeter-time-range.model';
import { TaximeterTimeRangeService } from '../service/taximeter-time-range.service';
import { TaximeterTimeRangeDeleteDialogComponent } from '../delete/taximeter-time-range-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-time-range',
  templateUrl: './taximeter-time-range.component.html',
})
export class TaximeterTimeRangeComponent implements OnInit {
  taximeterTimeRanges?: ITaximeterTimeRange[];
  isLoading = false;

  constructor(protected taximeterTimeRangeService: TaximeterTimeRangeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterTimeRangeService.query().subscribe(
      (res: HttpResponse<ITaximeterTimeRange[]>) => {
        this.isLoading = false;
        this.taximeterTimeRanges = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterTimeRange): number {
    return item.id!;
  }

  delete(taximeterTimeRange: ITaximeterTimeRange): void {
    const modalRef = this.modalService.open(TaximeterTimeRangeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterTimeRange = taximeterTimeRange;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
