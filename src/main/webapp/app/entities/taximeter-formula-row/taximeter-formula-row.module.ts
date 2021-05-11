import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterFormulaRowComponent } from './list/taximeter-formula-row.component';
import { TaximeterFormulaRowDetailComponent } from './detail/taximeter-formula-row-detail.component';
import { TaximeterFormulaRowUpdateComponent } from './update/taximeter-formula-row-update.component';
import { TaximeterFormulaRowDeleteDialogComponent } from './delete/taximeter-formula-row-delete-dialog.component';
import { TaximeterFormulaRowRoutingModule } from './route/taximeter-formula-row-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterFormulaRowRoutingModule],
  declarations: [
    TaximeterFormulaRowComponent,
    TaximeterFormulaRowDetailComponent,
    TaximeterFormulaRowUpdateComponent,
    TaximeterFormulaRowDeleteDialogComponent,
  ],
  entryComponents: [TaximeterFormulaRowDeleteDialogComponent],
})
export class TaximeterFormulaRowModule {}
