import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterTimeRange, TaximeterTimeRange } from '../taximeter-time-range.model';
import { TaximeterTimeRangeService } from '../service/taximeter-time-range.service';

@Component({
  selector: 'jhi-taximeter-time-range-update',
  templateUrl: './taximeter-time-range-update.component.html',
})
export class TaximeterTimeRangeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    created: [],
    updated: [],
  });

  constructor(
    protected taximeterTimeRangeService: TaximeterTimeRangeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterTimeRange }) => {
      if (taximeterTimeRange.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterTimeRange.created = today;
        taximeterTimeRange.updated = today;
      }

      this.updateForm(taximeterTimeRange);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterTimeRange = this.createFromForm();
    if (taximeterTimeRange.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterTimeRangeService.update(taximeterTimeRange));
    } else {
      this.subscribeToSaveResponse(this.taximeterTimeRangeService.create(taximeterTimeRange));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterTimeRange>>): void {
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

  protected updateForm(taximeterTimeRange: ITaximeterTimeRange): void {
    this.editForm.patchValue({
      id: taximeterTimeRange.id,
      name: taximeterTimeRange.name,
      description: taximeterTimeRange.description,
      created: taximeterTimeRange.created ? taximeterTimeRange.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterTimeRange.updated ? taximeterTimeRange.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITaximeterTimeRange {
    return {
      ...new TaximeterTimeRange(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
