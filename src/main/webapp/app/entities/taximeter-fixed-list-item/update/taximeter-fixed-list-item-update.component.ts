import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterFixedListItem, TaximeterFixedListItem } from '../taximeter-fixed-list-item.model';
import { TaximeterFixedListItemService } from '../service/taximeter-fixed-list-item.service';
import { ITaximeterFixedList } from 'app/entities/taximeter-fixed-list/taximeter-fixed-list.model';
import { TaximeterFixedListService } from 'app/entities/taximeter-fixed-list/service/taximeter-fixed-list.service';

@Component({
  selector: 'jhi-taximeter-fixed-list-item-update',
  templateUrl: './taximeter-fixed-list-item-update.component.html',
})
export class TaximeterFixedListItemUpdateComponent implements OnInit {
  isSaving = false;

  taximeterFixedListsSharedCollection: ITaximeterFixedList[] = [];

  editForm = this.fb.group({
    id: [],
    from: [null, [Validators.required]],
    to: [null, [Validators.required]],
    value: [null, [Validators.required]],
    valueReverse: [],
    created: [],
    updated: [],
    list: [null, Validators.required],
  });

  constructor(
    protected taximeterFixedListItemService: TaximeterFixedListItemService,
    protected taximeterFixedListService: TaximeterFixedListService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFixedListItem }) => {
      if (taximeterFixedListItem.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterFixedListItem.created = today;
        taximeterFixedListItem.updated = today;
      }

      this.updateForm(taximeterFixedListItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterFixedListItem = this.createFromForm();
    if (taximeterFixedListItem.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterFixedListItemService.update(taximeterFixedListItem));
    } else {
      this.subscribeToSaveResponse(this.taximeterFixedListItemService.create(taximeterFixedListItem));
    }
  }

  trackTaximeterFixedListById(index: number, item: ITaximeterFixedList): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterFixedListItem>>): void {
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

  protected updateForm(taximeterFixedListItem: ITaximeterFixedListItem): void {
    this.editForm.patchValue({
      id: taximeterFixedListItem.id,
      from: taximeterFixedListItem.from,
      to: taximeterFixedListItem.to,
      value: taximeterFixedListItem.value,
      valueReverse: taximeterFixedListItem.valueReverse,
      created: taximeterFixedListItem.created ? taximeterFixedListItem.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterFixedListItem.updated ? taximeterFixedListItem.updated.format(DATE_TIME_FORMAT) : null,
      list: taximeterFixedListItem.list,
    });

    this.taximeterFixedListsSharedCollection = this.taximeterFixedListService.addTaximeterFixedListToCollectionIfMissing(
      this.taximeterFixedListsSharedCollection,
      taximeterFixedListItem.list
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taximeterFixedListService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterFixedList[]>) => res.body ?? []))
      .pipe(
        map((taximeterFixedLists: ITaximeterFixedList[]) =>
          this.taximeterFixedListService.addTaximeterFixedListToCollectionIfMissing(taximeterFixedLists, this.editForm.get('list')!.value)
        )
      )
      .subscribe((taximeterFixedLists: ITaximeterFixedList[]) => (this.taximeterFixedListsSharedCollection = taximeterFixedLists));
  }

  protected createFromForm(): ITaximeterFixedListItem {
    return {
      ...new TaximeterFixedListItem(),
      id: this.editForm.get(['id'])!.value,
      from: this.editForm.get(['from'])!.value,
      to: this.editForm.get(['to'])!.value,
      value: this.editForm.get(['value'])!.value,
      valueReverse: this.editForm.get(['valueReverse'])!.value,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      list: this.editForm.get(['list'])!.value,
    };
  }
}
