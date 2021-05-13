import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterTimeRange } from '../taximeter-time-range.model';

@Component({
  selector: 'jhi-taximeter-time-range-detail',
  templateUrl: './taximeter-time-range-detail.component.html',
})
export class TaximeterTimeRangeDetailComponent implements OnInit {
  taximeterTimeRange: ITaximeterTimeRange | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterTimeRange }) => {
      this.taximeterTimeRange = taximeterTimeRange;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
