import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterTimeRangeItemComponent } from './list/taximeter-time-range-item.component';
import { TaximeterTimeRangeItemDetailComponent } from './detail/taximeter-time-range-item-detail.component';
import { TaximeterTimeRangeItemUpdateComponent } from './update/taximeter-time-range-item-update.component';
import { TaximeterTimeRangeItemDeleteDialogComponent } from './delete/taximeter-time-range-item-delete-dialog.component';
import { TaximeterTimeRangeItemRoutingModule } from './route/taximeter-time-range-item-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterTimeRangeItemRoutingModule],
  declarations: [
    TaximeterTimeRangeItemComponent,
    TaximeterTimeRangeItemDetailComponent,
    TaximeterTimeRangeItemUpdateComponent,
    TaximeterTimeRangeItemDeleteDialogComponent,
  ],
  entryComponents: [TaximeterTimeRangeItemDeleteDialogComponent],
})
export class TaximeterTimeRangeItemModule {}
