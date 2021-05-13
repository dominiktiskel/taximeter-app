import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterTimeRangeItem } from '../taximeter-time-range-item.model';

@Component({
  selector: 'jhi-taximeter-time-range-item-detail',
  templateUrl: './taximeter-time-range-item-detail.component.html',
})
export class TaximeterTimeRangeItemDetailComponent implements OnInit {
  taximeterTimeRangeItem: ITaximeterTimeRangeItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterTimeRangeItem }) => {
      this.taximeterTimeRangeItem = taximeterTimeRangeItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
