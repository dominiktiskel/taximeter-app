import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFixedList } from '../taximeter-fixed-list.model';
import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';
import { TaximeterFixedListDeleteDialogComponent } from '../delete/taximeter-fixed-list-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-fixed-list',
  templateUrl: './taximeter-fixed-list.component.html',
})
export class TaximeterFixedListComponent implements OnInit {
  taximeterFixedLists?: ITaximeterFixedList[];
  isLoading = false;

  constructor(protected taximeterFixedListService: TaximeterFixedListService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterFixedListService.query().subscribe(
      (res: HttpResponse<ITaximeterFixedList[]>) => {
        this.isLoading = false;
        this.taximeterFixedLists = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterFixedList): number {
    return item.id!;
  }

  delete(taximeterFixedList: ITaximeterFixedList): void {
    const modalRef = this.modalService.open(TaximeterFixedListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterFixedList = taximeterFixedList;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
