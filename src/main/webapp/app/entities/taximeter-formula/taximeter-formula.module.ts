import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterFormulaComponent } from './list/taximeter-formula.component';
import { TaximeterFormulaDetailComponent } from './detail/taximeter-formula-detail.component';
import { TaximeterFormulaUpdateComponent } from './update/taximeter-formula-update.component';
import { TaximeterFormulaDeleteDialogComponent } from './delete/taximeter-formula-delete-dialog.component';
import { TaximeterFormulaRoutingModule } from './route/taximeter-formula-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterFormulaRoutingModule],
  declarations: [
    TaximeterFormulaComponent,
    TaximeterFormulaDetailComponent,
    TaximeterFormulaUpdateComponent,
    TaximeterFormulaDeleteDialogComponent,
  ],
  entryComponents: [TaximeterFormulaDeleteDialogComponent],
})
export class TaximeterFormulaModule {}
