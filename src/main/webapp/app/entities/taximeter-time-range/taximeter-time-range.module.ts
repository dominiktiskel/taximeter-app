import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterTimeRangeComponent } from './list/taximeter-time-range.component';
import { TaximeterTimeRangeDetailComponent } from './detail/taximeter-time-range-detail.component';
import { TaximeterTimeRangeUpdateComponent } from './update/taximeter-time-range-update.component';
import { TaximeterTimeRangeDeleteDialogComponent } from './delete/taximeter-time-range-delete-dialog.component';
import { TaximeterTimeRangeRoutingModule } from './route/taximeter-time-range-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterTimeRangeRoutingModule],
  declarations: [
    TaximeterTimeRangeComponent,
    TaximeterTimeRangeDetailComponent,
    TaximeterTimeRangeUpdateComponent,
    TaximeterTimeRangeDeleteDialogComponent,
  ],
  entryComponents: [TaximeterTimeRangeDeleteDialogComponent],
})
export class TaximeterTimeRangeModule {}
