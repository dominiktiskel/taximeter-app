import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterOfferGroup, TaximeterOfferGroup } from '../taximeter-offer-group.model';
import { TaximeterOfferGroupService } from '../service/taximeter-offer-group.service';
import { ITaximeterOffer } from 'app/entities/taximeter-offer/taximeter-offer.model';
import { TaximeterOfferService } from 'app/entities/taximeter-offer/service/taximeter-offer.service';

@Component({
  selector: 'jhi-taximeter-offer-group-update',
  templateUrl: './taximeter-offer-group-update.component.html',
})
export class TaximeterOfferGroupUpdateComponent implements OnInit {
  isSaving = false;

  taximeterOffersSharedCollection: ITaximeterOffer[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    invoiceAs: [],
    chargeBy: [null, [Validators.required]],
    created: [],
    updated: [],
    offer: [null, Validators.required],
  });

  constructor(
    protected taximeterOfferGroupService: TaximeterOfferGroupService,
    protected taximeterOfferService: TaximeterOfferService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterOfferGroup }) => {
      if (taximeterOfferGroup.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterOfferGroup.created = today;
        taximeterOfferGroup.updated = today;
      }

      this.updateForm(taximeterOfferGroup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterOfferGroup = this.createFromForm();
    if (taximeterOfferGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterOfferGroupService.update(taximeterOfferGroup));
    } else {
      this.subscribeToSaveResponse(this.taximeterOfferGroupService.create(taximeterOfferGroup));
    }
  }

  trackTaximeterOfferById(index: number, item: ITaximeterOffer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterOfferGroup>>): void {
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

  protected updateForm(taximeterOfferGroup: ITaximeterOfferGroup): void {
    this.editForm.patchValue({
      id: taximeterOfferGroup.id,
      name: taximeterOfferGroup.name,
      description: taximeterOfferGroup.description,
      invoiceAs: taximeterOfferGroup.invoiceAs,
      chargeBy: taximeterOfferGroup.chargeBy,
      created: taximeterOfferGroup.created ? taximeterOfferGroup.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterOfferGroup.updated ? taximeterOfferGroup.updated.format(DATE_TIME_FORMAT) : null,
      offer: taximeterOfferGroup.offer,
    });

    this.taximeterOffersSharedCollection = this.taximeterOfferService.addTaximeterOfferToCollectionIfMissing(
      this.taximeterOffersSharedCollection,
      taximeterOfferGroup.offer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taximeterOfferService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterOffer[]>) => res.body ?? []))
      .pipe(
        map((taximeterOffers: ITaximeterOffer[]) =>
          this.taximeterOfferService.addTaximeterOfferToCollectionIfMissing(taximeterOffers, this.editForm.get('offer')!.value)
        )
      )
      .subscribe((taximeterOffers: ITaximeterOffer[]) => (this.taximeterOffersSharedCollection = taximeterOffers));
  }

  protected createFromForm(): ITaximeterOfferGroup {
    return {
      ...new TaximeterOfferGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      invoiceAs: this.editForm.get(['invoiceAs'])!.value,
      chargeBy: this.editForm.get(['chargeBy'])!.value,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
      offer: this.editForm.get(['offer'])!.value,
    };
  }
}
