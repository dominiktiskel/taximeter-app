import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaximeterFixedList } from '../taximeter-fixed-list.model';

@Component({
  selector: 'jhi-taximeter-fixed-list-detail',
  templateUrl: './taximeter-fixed-list-detail.component.html',
})
export class TaximeterFixedListDetailComponent implements OnInit {
  taximeterFixedList: ITaximeterFixedList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFixedList }) => {
      this.taximeterFixedList = taximeterFixedList;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
