import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITaximeterFormulaRow, TaximeterFormulaRow } from '../taximeter-formula-row.model';
import { TaximeterFormulaRowService } from '../service/taximeter-formula-row.service';
import { ITaximeterFormula } from 'app/entities/taximeter-formula/taximeter-formula.model';
import { TaximeterFormulaService } from 'app/entities/taximeter-formula/service/taximeter-formula.service';

@Component({
  selector: 'jhi-taximeter-formula-row-update',
  templateUrl: './taximeter-formula-row-update.component.html',
})
export class TaximeterFormulaRowUpdateComponent implements OnInit {
  isSaving = false;

  taximeterFormulasSharedCollection: ITaximeterFormula[] = [];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    value: [null, [Validators.required]],
    step: [null, [Validators.required]],
    granulation: [],
    formula: [null, Validators.required],
  });

  constructor(
    protected taximeterFormulaRowService: TaximeterFormulaRowService,
    protected taximeterFormulaService: TaximeterFormulaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taximeterFormulaRow }) => {
      this.updateForm(taximeterFormulaRow);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taximeterFormulaRow = this.createFromForm();
    if (taximeterFormulaRow.id !== undefined) {
      this.subscribeToSaveResponse(this.taximeterFormulaRowService.update(taximeterFormulaRow));
    } else {
      this.subscribeToSaveResponse(this.taximeterFormulaRowService.create(taximeterFormulaRow));
    }
  }

  trackTaximeterFormulaById(index: number, item: ITaximeterFormula): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaximeterFormulaRow>>): void {
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

  protected updateForm(taximeterFormulaRow: ITaximeterFormulaRow): void {
    this.editForm.patchValue({
      id: taximeterFormulaRow.id,
      type: taximeterFormulaRow.type,
      value: taximeterFormulaRow.value,
      step: taximeterFormulaRow.step,
      granulation: taximeterFormulaRow.granulation,
      formula: taximeterFormulaRow.formula,
    });

    this.taximeterFormulasSharedCollection = this.taximeterFormulaService.addTaximeterFormulaToCollectionIfMissing(
      this.taximeterFormulasSharedCollection,
      taximeterFormulaRow.formula
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taximeterFormulaService
      .query()
      .pipe(map((res: HttpResponse<ITaximeterFormula[]>) => res.body ?? []))
      .pipe(
        map((taximeterFormulas: ITaximeterFormula[]) =>
          this.taximeterFormulaService.addTaximeterFormulaToCollectionIfMissing(taximeterFormulas, this.editForm.get('formula')!.value)
        )
      )
      .subscribe((taximeterFormulas: ITaximeterFormula[]) => (this.taximeterFormulasSharedCollection = taximeterFormulas));
  }

  protected createFromForm(): ITaximeterFormulaRow {
    return {
      ...new TaximeterFormulaRow(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      value: this.editForm.get(['value'])!.value,
      step: this.editForm.get(['step'])!.value,
      granulation: this.editForm.get(['granulation'])!.value,
      formula: this.editForm.get(['formula'])!.value,
    };
  }
}
