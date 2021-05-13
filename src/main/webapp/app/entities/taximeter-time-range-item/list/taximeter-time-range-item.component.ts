import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterTimeRangeItem } from '../taximeter-time-range-item.model';
import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';
import { TaximeterTimeRangeItemDeleteDialogComponent } from '../delete/taximeter-time-range-item-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-time-range-item',
  templateUrl: './taximeter-time-range-item.component.html',
})
export class TaximeterTimeRangeItemComponent implements OnInit {
  taximeterTimeRangeItems?: ITaximeterTimeRangeItem[];
  isLoading = false;

  constructor(protected taximeterTimeRangeItemService: TaximeterTimeRangeItemService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterTimeRangeItemService.query().subscribe(
      (res: HttpResponse<ITaximeterTimeRangeItem[]>) => {
        this.isLoading = false;
        this.taximeterTimeRangeItems = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterTimeRangeItem): number {
    return item.id!;
  }

  delete(taximeterTimeRangeItem: ITaximeterTimeRangeItem): void {
    const modalRef = this.modalService.open(TaximeterTimeRangeItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterTimeRangeItem = taximeterTimeRangeItem;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
