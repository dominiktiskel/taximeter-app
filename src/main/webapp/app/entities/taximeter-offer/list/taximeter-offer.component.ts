import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterOffer } from '../taximeter-offer.model';
import { TaximeterOfferService } from '../service/taximeter-offer.service';
import { TaximeterOfferDeleteDialogComponent } from '../delete/taximeter-offer-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-offer',
  templateUrl: './taximeter-offer.component.html',
})
export class TaximeterOfferComponent implements OnInit {
  taximeterOffers?: ITaximeterOffer[];
  isLoading = false;

  constructor(protected taximeterOfferService: TaximeterOfferService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterOfferService.query().subscribe(
      (res: HttpResponse<ITaximeterOffer[]>) => {
        this.isLoading = false;
        this.taximeterOffers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterOffer): number {
    return item.id!;
  }

  delete(taximeterOffer: ITaximeterOffer): void {
    const modalRef = this.modalService.open(TaximeterOfferDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterOffer = taximeterOffer;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
