import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterOfferItem } from '../taximeter-offer-item.model';

@Component({
  selector: 'jhi-taximeter-offer-item-detail',
  templateUrl: './taximeter-offer-item-detail.component.html',
})
export class TaximeterOfferItemDetailComponent implements OnInit {
  taximeterOfferItem: ITaximeterOfferItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterOfferItem }) => {
      this.taximeterOfferItem = taximeterOfferItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
