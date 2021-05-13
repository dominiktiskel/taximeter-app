import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFixedListItem } from '../taximeter-fixed-list-item.model';
import { TaximeterFixedListItemService } from '../service/taximeter-fixed-list-item.service';
import { TaximeterFixedListItemDeleteDialogComponent } from '../delete/taximeter-fixed-list-item-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-fixed-list-item',
  templateUrl: './taximeter-fixed-list-item.component.html',
})
export class TaximeterFixedListItemComponent implements OnInit {
  taximeterFixedListItems?: ITaximeterFixedListItem[];
  isLoading = false;

  constructor(protected taximeterFixedListItemService: TaximeterFixedListItemService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterFixedListItemService.query().subscribe(
      (res: HttpResponse<ITaximeterFixedListItem[]>) => {
        this.isLoading = false;
        this.taximeterFixedListItems = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterFixedListItem): number {
    return item.id!;
  }

  delete(taximeterFixedListItem: ITaximeterFixedListItem): void {
    const modalRef = this.modalService.open(TaximeterFixedListItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterFixedListItem = taximeterFixedListItem;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
