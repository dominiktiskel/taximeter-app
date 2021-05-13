import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterOfferItem, TaximeterOfferItem } from '../taximeter-offer-item.model';
import { TaximeterOfferItemService } from '../service/taximeter-offer-item.service';
import { ITaximeterFixedList } from 'app/entities/taximeter-fixed-list/taximeter-fixed-list.model';
import { TaximeterFixedListService } from 'app/entities/taximeter-fixed-list/service/taximeter-fixed-list.service';
import { ITaximeterFormula } from 'app/entities/taximeter-formula/taximeter-formula.model';
import { TaximeterFormulaService } from 'app/entities/taximeter-formula/service/taximeter-formula.service';
import { ITaximeterTimeRange } from 'app/entities/taximeter-time-range/taximeter-time-range.model';
import { TaximeterTimeRangeService } from 'app/entities/taximeter-time-range/service/taximeter-time-range.service';
import { IPreference } from 'app/entities/preference/preference.model';
import { PreferenceService } from 'app/entities/preference/service/preference.service';
import { ITaximeterOfferGroup } from 'app/entities/taximeter-offer-group/taximeter-offer-group.model';
import { TaximeterOfferGroupService } from 'app/entities/taximeter-offer-group/service/taximeter-offer-group.service';

@Component({
  selector: 'jhi-taximeter-offer-item-update',
  templateUrl: './taximeter-offer-item-update.component.html',
})
export class TaximeterOfferItemUpdateComponent implements OnInit {
  isSaving = false;

  taximeterFixedListsSharedCollection: ITaximeterFixedList[] = [];
  taximeterFormulasSharedCollection: ITaximeterFormula[] = [];
  taximeterTimeRangesSharedCollection: ITaximeterTimeRange[] = [];
  preferencesSharedCollection: IPreference[] = [];
  taximeterOfferGroupsSharedCollection: ITaximeterOfferGroup[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    billCompanyPays: [],
    customerPays: [],
    taxiGets: [],
    taxiPays: [],
    created: [],
    updated: [],
    fixedList1: [],
    fixedList2: [],
    formula: [],
    timeRange: [],
    preferences: [],
    group: [null, Validators.required],
  });

  constructor(
    protected taximeterOfferItemService: TaximeterOfferItemService,
    protected taximeterFixedListService: TaximeterFixedListService,
    protected taximeterFormulaService: TaximeterFormulaService,
    protected taximeterTimeRangeService: TaximeterTimeRangeService,
    protected preferenceService: PreferenceService,
    protected taximeterOfferGroupService: TaximeterOfferGroupService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterOfferItem }) => {
      if (taximeterOfferItem.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterOfferItem.created = today;
        taximeterOfferItem.updated = today;
      }

      this.updateForm(taximeterOfferItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterOfferItem = this.createFromForm();
    if (taximeterOfferItem.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterOfferItemService.update(taximeterOfferItem));
    } else {
      this.subscribeToSaveResponse(this.taximeterOfferItemService.create(taximeterOfferItem));
    }
  }

  trackTaximeterFixedListById(index: number, item: ITaximeterFixedList): number {
    return item.id!;
  }

  trackTaximeterFormulaById(index: number, item: ITaximeterFormula): number {
    return item.id!;
  }

  trackTaximeterTimeRangeById(index: number, item: ITaximeterTimeRange): number {
    return item.id!;
  }

  trackPreferenceById(index: number, item: IPreference): number {
    return item.id!;
  }

  trackTaximeterOfferGroupById(index: number, item: ITaximeterOfferGroup): number {
    return item.id!;
  }

  getSelectedPreference(option: IPreference, selectedVals?: IPreference[]): IPreference {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterOfferItem>>): void {
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

  protected updateForm(taximeterOfferItem: ITaximeterOfferItem): void {
    this.editForm.patchValue({
      id: taximeterOfferItem.id,
      name: taximeterOfferItem.name,
      description: taximeterOfferItem.description,
      billCompanyPays: taximeterOfferItem.billCompanyPays,
      customerPays: taximeterOfferItem.customerPays,
      taxiGets: taximeterOfferItem.taxiGets,
      taxiPays: taximeterOfferItem.taxiPays,
      created: taximeterOfferItem.created ? taximeterOfferItem.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterOfferItem.updated ? taximeterOfferItem.updated.format(DATE_TIME_FORMAT) : null,
      fixedList1: taximeterOfferItem.fixedList1,
      fixedList2: taximeterOfferItem.fixedList2,
      formula: taximeterOfferItem.formula,
      timeRange: taximeterOfferItem.timeRange,
      preferences: taximeterOfferItem.preferences,
      group: taximeterOfferItem.group,
    });

    this.taximeterFixedListsSharedCollection = this.taximeterFixedListService.addTaximeterFixedListToCollectionIfMissing(
      this.taximeterFixedListsSharedCollection,
      taximeterOfferItem.fixedList1,
      taximeterOfferItem.fixedList2
    );
    this.taximeterFormulasSharedCollection = this.taximeterFormulaService.addTaximeterFormulaToCollectionIfMissing(
      this.taximeterFormulasSharedCollection,
      taximeterOfferItem.formula
    );
    this.taximeterTimeRangesSharedCollection = this.taximeterTimeRangeService.addTaximeterTimeRangeToCollectionIfMissing(
      this.taximeterTimeRangesSharedCollection,
      taximeterOfferItem.timeRange
    );
    this.preferencesSharedCollection = this.preferenceService.addPreferenceToCollectionIfMissing(
      this.preferencesSharedCollection,
      ...(taximeterOfferItem.preferences ?? [])
    );
    this.taximeterOfferGroupsSharedCollection = this.taximeterOfferGroupService.addTaximeterOfferGroupToCollectionIfMissing(
      this.taximeterOfferGroupsSharedCollection,
      taximeterOfferItem.group
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taximeterFixedListService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterFixedList[]>) => res.body ?? []))
      .pipe(
        map((taximeterFixedLists: ITaximeterFixedList[]) =>
          this.taximeterFixedListService.addTaximeterFixedListToCollectionIfMissing(
            taximeterFixedLists,
            this.editForm.get('fixedList1')!.value,
            this.editForm.get('fixedList2')!.value
          )
        )
      )
      .subscribe((taximeterFixedLists: ITaximeterFixedList[]) => (this.taximeterFixedListsSharedCollection = taximeterFixedLists));

    this.taximeterFormulaService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterFormula[]>) => res.body ?? []))
      .pipe(
        map((taximeterFormulas: ITaximeterFormula[]) =>
          this.taximeterFormulaService.addTaximeterFormulaToCollectionIfMissing(taximeterFormulas, this.editForm.get('formula')!.value)
        )
      )
      .subscribe((taximeterFormulas: ITaximeterFormula[]) => (this.taximeterFormulasSharedCollection = taximeterFormulas));

    this.taximeterTimeRangeService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterTimeRange[]>) => res.body ?? []))
      .pipe(
        map((taximeterTimeRanges: ITaximeterTimeRange[]) =>
          this.taximeterTimeRangeService.addTaximeterTimeRangeToCollectionIfMissing(
            taximeterTimeRanges,
            this.editForm.get('timeRange')!.value
          )
        )
      )
      .subscribe((taximeterTimeRanges: ITaximeterTimeRange[]) => (this.taximeterTimeRangesSharedCollection = taximeterTimeRanges));

    this.preferenceService
      .query()
      .pipe(map((res: HttpResponse<IPreference[]>) => res.body ?? []))
      .pipe(
        map((preferences: IPreference[]) =>
          this.preferenceService.addPreferenceToCollectionIfMissing(preferences, ...(this.editForm.get('preferences')!.value ?? []))
        )
      )
      .subscribe((preferences: IPreference[]) => (this.preferencesSharedCollection = preferences));

    this.taximeterOfferGroupService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterOfferGroup[]>) => res.body ?? []))
      .pipe(
        map((taximeterOfferGroups: ITaximeterOfferGroup[]) =>
          this.taximeterOfferGroupService.addTaximeterOfferGroupToCollectionIfMissing(
            taximeterOfferGroups,
            this.editForm.get('group')!.value
          )
        )
      )
      .subscribe((taximeterOfferGroups: ITaximeterOfferGroup[]) => (this.taximeterOfferGroupsSharedCollection = taximeterOfferGroups));
  }

  protected createFromForm(): ITaximeterOfferItem {
    return {
      ...new TaximeterOfferItem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      billCompanyPays: this.editForm.get(['billCompanyPays'])!.value,
      customerPays: this.editForm.get(['customerPays'])!.value,
      taxiGets: this.editForm.get(['taxiGets'])!.value,
      taxiPays: this.editForm.get(['taxiPays'])!.value,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      fixedList1: this.editForm.get(['fixedList1'])!.value,
      fixedList2: this.editForm.get(['fixedList2'])!.value,
      formula: this.editForm.get(['formula'])!.value,
      timeRange: this.editForm.get(['timeRange'])!.value,
      preferences: this.editForm.get(['preferences'])!.value,
      group: this.editForm.get(['group'])!.value,
    };
  }
}
