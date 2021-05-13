import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPreference, Preference } from '../preference.model';
import { PreferenceService } from '../service/preference.service';

@Component({
  selector: 'jhi-preference-update',
  templateUrl: './preference-update.component.html',
})
export class PreferenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected preferenceService: PreferenceService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ preference }) => {
      this.updateForm(preference);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const preference = this.createFromForm();
    if (preference.id !== undefined) {
      this.subscribeToSaveResponse(this.preferenceService.update(preference));
    } else {
      this.subscribeToSaveResponse(this.preferenceService.create(preference));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPreference>>): void {
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

  protected updateForm(preference: IPreference): void {
    this.editForm.patchValue({
      id: preference.id,
    });
  }

  protected createFromForm(): IPreference {
    return {
      ...new Preference(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
