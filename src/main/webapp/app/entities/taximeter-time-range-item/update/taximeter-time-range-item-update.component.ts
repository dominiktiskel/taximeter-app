import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterTimeRangeItem, TaximeterTimeRangeItem } from '../taximeter-time-range-item.model';
import { TaximeterTimeRangeItemService } from '../service/taximeter-time-range-item.service';
import { ITaximeterTimeRange } from 'app/entities/taximeter-time-range/taximeter-time-range.model';
import { TaximeterTimeRangeService } from 'app/entities/taximeter-time-range/service/taximeter-time-range.service';

@Component({
  selector: 'jhi-taximeter-time-range-item-update',
  templateUrl: './taximeter-time-range-item-update.component.html',
})
export class TaximeterTimeRangeItemUpdateComponent implements OnInit {
  isSaving = false;

  taximeterTimeRangesSharedCollection: ITaximeterTimeRange[] = [];

  editForm = this.fb.group({
    id: [],
    day: [null, [Validators.required]],
    hours: [null, [Validators.required]],
    created: [],
    updated: [],
    range: [null, Validators.required],
  });

  constructor(
    protected taximeterTimeRangeItemService: TaximeterTimeRangeItemService,
    protected taximeterTimeRangeService: TaximeterTimeRangeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterTimeRangeItem }) => {
      if (taximeterTimeRangeItem.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterTimeRangeItem.created = today;
        taximeterTimeRangeItem.updated = today;
      }

      this.updateForm(taximeterTimeRangeItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterTimeRangeItem = this.createFromForm();
    if (taximeterTimeRangeItem.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterTimeRangeItemService.update(taximeterTimeRangeItem));
    } else {
      this.subscribeToSaveResponse(this.taximeterTimeRangeItemService.create(taximeterTimeRangeItem));
    }
  }

  trackTaximeterTimeRangeById(index: number, item: ITaximeterTimeRange): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterTimeRangeItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(taximeterTimeRangeItem: ITaximeterTimeRangeItem): void {
    this.editForm.patchValue({
      id: taximeterTimeRangeItem.id,
      day: taximeterTimeRangeItem.day,
      hours: taximeterTimeRangeItem.hours,
      created: taximeterTimeRangeItem.created ? taximeterTimeRangeItem.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterTimeRangeItem.updated ? taximeterTimeRangeItem.updated.format(DATE_TIME_FORMAT) : null,
      range: taximeterTimeRangeItem.range,
    });

    this.taximeterTimeRangesSharedCollection = this.taximeterTimeRangeService.addTaximeterTimeRangeToCollectionIfMissing(
      this.taximeterTimeRangesSharedCollection,
      taximeterTimeRangeItem.range
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taximeterTimeRangeService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterTimeRange[]>) => res.body ?? []))
      .pipe(
        map((taximeterTimeRanges: ITaximeterTimeRange[]) =>
          this.taximeterTimeRangeService.addTaximeterTimeRangeToCollectionIfMissing(taximeterTimeRanges, this.editForm.get('range')!.value)
        )
      )
      .subscribe((taximeterTimeRanges: ITaximeterTimeRange[]) => (this.taximeterTimeRangesSharedCollection = taximeterTimeRanges));
  }

  protected createFromForm(): ITaximeterTimeRangeItem {
    return {
      ...new TaximeterTimeRangeItem(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      hours: this.editForm.get(['hours'])!.value,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      range: this.editForm.get(['range'])!.value,
    };
  }
}
