import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterOfferGroup } from '../taximeter-offer-group.model';
import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';
import { TaximeterOfferGroupDeleteDialogComponent } from '../delete/taximeter-offer-group-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-offer-group',
  templateUrl: './taximeter-offer-group.component.html',
})
export class TaximeterOfferGroupComponent implements OnInit {
  taximeterOfferGroups?: ITaximeterOfferGroup[];
  isLoading = false;

  constructor(protected taximeterOfferGroupService: TaximeterOfferGroupService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterOfferGroupService.query().subscribe(
      (res: HttpResponse<ITaximeterOfferGroup[]>) => {
        this.isLoading = false;
        this.taximeterOfferGroups = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterOfferGroup): number {
    return item.id!;
  }

  delete(taximeterOfferGroup: ITaximeterOfferGroup): void {
    const modalRef = this.modalService.open(TaximeterOfferGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterOfferGroup = taximeterOfferGroup;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
