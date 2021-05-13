import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterOfferItem } from '../taximeter-offer-item.model';
import { TaximeterOfferItemService } from '../service/taximeter-offer-item.service';
import { TaximeterOfferItemDeleteDialogComponent } from '../delete/taximeter-offer-item-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-offer-item',
  templateUrl: './taximeter-offer-item.component.html',
})
export class TaximeterOfferItemComponent implements OnInit {
  taximeterOfferItems?: ITaximeterOfferItem[];
  isLoading = false;

  constructor(protected taximeterOfferItemService: TaximeterOfferItemService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterOfferItemService.query().subscribe(
      (res: HttpResponse<ITaximeterOfferItem[]>) => {
        this.isLoading = false;
        this.taximeterOfferItems = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterOfferItem): number {
    return item.id!;
  }

  delete(taximeterOfferItem: ITaximeterOfferItem): void {
    const modalRef = this.modalService.open(TaximeterOfferItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterOfferItem = taximeterOfferItem;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
