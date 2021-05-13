import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterFixedListItem } from '../taximeter-fixed-list-item.model';

@Component({
  selector: 'jhi-taximeter-fixed-list-item-detail',
  templateUrl: './taximeter-fixed-list-item-detail.component.html',
})
export class TaximeterFixedListItemDetailComponent implements OnInit {
  taximeterFixedListItem: ITaximeterFixedListItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFixedListItem }) => {
      this.taximeterFixedListItem = taximeterFixedListItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
