import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterOffer } from '../taximeter-offer.model';

@Component({
  selector: 'jhi-taximeter-offer-detail',
  templateUrl: './taximeter-offer-detail.component.html',
})
export class TaximeterOfferDetailComponent implements OnInit {
  taximeterOffer: ITaximeterOffer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterOffer }) => {
      this.taximeterOffer = taximeterOffer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
