import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterFixedListItemComponent } from './list/taximeter-fixed-list-item.component';
import { TaximeterFixedListItemDetailComponent } from './detail/taximeter-fixed-list-item-detail.component';
import { TaximeterFixedListItemUpdateComponent } from './update/taximeter-fixed-list-item-update.component';
import { TaximeterFixedListItemDeleteDialogComponent } from './delete/taximeter-fixed-list-item-delete-dialog.component';
import { TaximeterFixedListItemRoutingModule } from './route/taximeter-fixed-list-item-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterFixedListItemRoutingModule],
  declarations: [
    TaximeterFixedListItemComponent,
    TaximeterFixedListItemDetailComponent,
    TaximeterFixedListItemUpdateComponent,
    TaximeterFixedListItemDeleteDialogComponent,
  ],
  entryComponents: [TaximeterFixedListItemDeleteDialogComponent],
})
export class TaximeterFixedListItemModule {}
