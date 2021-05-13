import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterFixedList, TaximeterFixedList } from '../taximeter-fixed-list.model';
import { TaximeterFixedListService } from '../service/taximeter-fixed-list.service';

@Component({
  selector: 'jhi-taximeter-fixed-list-update',
  templateUrl: './taximeter-fixed-list-update.component.html',
})
export class TaximeterFixedListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    type: [null, [Validators.required]],
    created: [],
    updated: [],
  });

  constructor(
    protected taximeterFixedListService: TaximeterFixedListService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFixedList }) => {
      if (taximeterFixedList.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterFixedList.created = today;
        taximeterFixedList.updated = today;
      }

      this.updateForm(taximeterFixedList);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterFixedList = this.createFromForm();
    if (taximeterFixedList.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterFixedListService.update(taximeterFixedList));
    } else {
      this.subscribeToSaveResponse(this.taximeterFixedListService.create(taximeterFixedList));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterFixedList>>): void {
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

  protected updateForm(taximeterFixedList: ITaximeterFixedList): void {
    this.editForm.patchValue({
      id: taximeterFixedList.id,
      name: taximeterFixedList.name,
      description: taximeterFixedList.description,
      type: taximeterFixedList.type,
      created: taximeterFixedList.created ? taximeterFixedList.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterFixedList.updated ? taximeterFixedList.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITaximeterFixedList {
    return {
      ...new TaximeterFixedList(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
