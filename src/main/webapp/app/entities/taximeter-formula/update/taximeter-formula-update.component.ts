import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

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
    type: [null, [Validators.required]],
    name: [null, [Validators.required]],
    active: [null, [Validators.required]],
  });

  constructor(
    protected taximeterFormulaService: TaximeterFormulaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFormula }) => {
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
      type: taximeterFormula.type,
      name: taximeterFormula.name,
      active: taximeterFormula.active,
    });
  }

  protected createFromForm(): ITaximeterFormula {
    return {
      ...new TaximeterFormula(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      name: this.editForm.get(['name'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }
}
