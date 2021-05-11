import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFormulaRow } from '../taximeter-formula-row.model';
import { TaximeterFormulaRowService } from '../service/taximeter-formula-row.service';
import { TaximeterFormulaRowDeleteDialogComponent } from '../delete/taximeter-formula-row-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-formula-row',
  templateUrl: './taximeter-formula-row.component.html',
})
export class TaximeterFormulaRowComponent implements OnInit {
  taximeterFormulaRows?: ITaximeterFormulaRow[];
  isLoading = false;

  constructor(protected taximeterFormulaRowService: TaximeterFormulaRowService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterFormulaRowService.query().subscribe(
      (res: HttpResponse<ITaximeterFormulaRow[]>) => {
        this.isLoading = false;
        this.taximeterFormulaRows = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterFormulaRow): number {
    return item.id!;
  }

  delete(taximeterFormulaRow: ITaximeterFormulaRow): void {
    const modalRef = this.modalService.open(TaximeterFormulaRowDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterFormulaRow = taximeterFormulaRow;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
