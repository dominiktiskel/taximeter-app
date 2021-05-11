import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterFormulaRow } from '../taximeter-formula-row.model';

@Component({
  selector: 'jhi-taximeter-formula-row-detail',
  templateUrl: './taximeter-formula-row-detail.component.html',
})
export class TaximeterFormulaRowDetailComponent implements OnInit {
  taximeterFormulaRow: ITaximeterFormulaRow | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFormulaRow }) => {
      this.taximeterFormulaRow = taximeterFormulaRow;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
