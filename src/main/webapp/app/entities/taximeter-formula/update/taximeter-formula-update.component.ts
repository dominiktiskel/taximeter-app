import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITaximeterFormula, TaximeterFormula } from '../taximeter-formula.model';
import { TaximeterFormulaService } from '../service/taximeter-formula.service';

@Component({
  selector: 'jhi-taximeter-formula-update',
  templateUrl: './taximeter-formula-update.component.html',
})
export class TaximeterFormulaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    type: [null, [Validators.required]],
    chargeBy: [null, [Validators.required]],
    jsonData: [null, [Validators.required]],
    created: [],
    updated: [],
  });

  constructor(
    protected taximeterFormulaService: TaximeterFormulaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFormula }) => {
      if (taximeterFormula.id === undefined) {
        const today = dayjs().startOf('day');
        taximeterFormula.created = today;
        taximeterFormula.updated = today;
      }

      this.updateForm(taximeterFormula);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterFormula = this.createFromForm();
    if (taximeterFormula.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterFormulaService.update(taximeterFormula));
    } else {
      this.subscribeToSaveResponse(this.taximeterFormulaService.create(taximeterFormula));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterFormula>>): void {
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

  protected updateForm(taximeterFormula: ITaximeterFormula): void {
    this.editForm.patchValue({
      id: taximeterFormula.id,
      name: taximeterFormula.name,
      description: taximeterFormula.description,
      type: taximeterFormula.type,
      chargeBy: taximeterFormula.chargeBy,
      jsonData: taximeterFormula.jsonData,
      created: taximeterFormula.created ? taximeterFormula.created.format(DATE_TIME_FORMAT) : null,
      updated: taximeterFormula.updated ? taximeterFormula.updated.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITaximeterFormula {
    return {
      ...new TaximeterFormula(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      chargeBy: this.editForm.get(['chargeBy'])!.value,
      jsonData: this.editForm.get(['jsonData'])!.value,
      created: this.editForm.get(['created'])!.value ? dayjs(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      updated: this.editForm.get(['updated'])!.value ? dayjs(this.editForm.get(['updated'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
