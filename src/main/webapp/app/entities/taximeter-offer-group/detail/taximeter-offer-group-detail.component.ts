import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterOfferGroup } from '../taximeter-offer-group.model';

@Component({
  selector: 'jhi-taximeter-offer-group-detail',
  templateUrl: './taximeter-offer-group-detail.component.html',
})
export class TaximeterOfferGroupDetailComponent implements OnInit {
  taximeterOfferGroup: ITaximeterOfferGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterOfferGroup }) => {
      this.taximeterOfferGroup = taximeterOfferGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
