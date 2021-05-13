import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterFormula } from '../taximeter-formula.model';

@Component({
  selector: 'jhi-taximeter-formula-detail',
  templateUrl: './taximeter-formula-detail.component.html',
})
export class TaximeterFormulaDetailComponent implements OnInit {
  taximeterFormula: ITaximeterFormula | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFormula }) => {
      this.taximeterFormula = taximeterFormula;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
