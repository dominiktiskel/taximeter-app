import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaximeterFormula } from '../taximeter-formula.model';
import { TaximeterFormulaService } from '../service/taximeter-formula.service';
import { TaximeterFormulaDeleteDialogComponent } from '../delete/taximeter-formula-delete-dialog.component';

@Component({
  selector: 'jhi-taximeter-formula',
  templateUrl: './taximeter-formula.component.html',
})
export class TaximeterFormulaComponent implements OnInit {
  taximeterFormulas?: ITaximeterFormula[];
  isLoading = false;

  constructor(protected taximeterFormulaService: TaximeterFormulaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taximeterFormulaService.query().subscribe(
      (res: HttpResponse<ITaximeterFormula[]>) => {
        this.isLoading = false;
        this.taximeterFormulas = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaximeterFormula): number {
    return item.id!;
  }

  delete(taximeterFormula: ITaximeterFormula): void {
    const modalRef = this.modalService.open(TaximeterFormulaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taximeterFormula = taximeterFormula;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
