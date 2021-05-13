import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterOffer, TaximeterOffer } from '../taximeter-offer.model';
import { TaximeterOfferService } from '../service/taximeter-offer.service';

@Component({
  selector: 'jhi-taximeter-offer-update',
  templateUrl: './taximeter-offer-update.component.html',
})
export class TaximeterOfferUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    validFrom: [],
    validTo: [],
    created: [],
    updated: [],
  });

  constructor(
    protected taximeterOfferService: TaximeterOfferService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterOffer }) => {
      if (taximeterOffer.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterOffer.validFrom = today;
        taximeterOffer.validTo = today;
        taximeterOffer.created = today;
        taximeterOffer.updated = today;
      }

      this.updateForm(taximeterOffer);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterOffer = this.createFromForm();
    if (taximeterOffer.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterOfferService.update(taximeterOffer));
    } else {
      this.subscribeToSaveResponse(this.taximeterOfferService.create(taximeterOffer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterOffer>>): void {
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

  protected updateForm(taximeterOffer: ITaximeterOffer): void {
    this.editForm.patchValue({
      id: taximeterOffer.id,
      name: taximeterOffer.name,
      description: taximeterOffer.description,
      validFrom: taximeterOffer.validFrom ? taximeterOffer.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: taximeterOffer.validTo ? taximeterOffer.validTo.format(DATE_TIME_FORMAT) : null,
      created: taximeterOffer.created ? taximeterOffer.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterOffer.updated ? taximeterOffer.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITaximeterOffer {
    return {
      ...new TaximeterOffer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      validFrom: this.editForm.get(['validFrom'])!.value ? dayjs(this.editForm.get(['validFrom'])!.value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo'])!.value ? dayjs(this.editForm.get(['validTo'])!.value, DATE_TIME_FORMAT) : undefined,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
