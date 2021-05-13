import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterOfferItemComponent } from './list/taximeter-offer-item.component';
import { TaximeterOfferItemDetailComponent } from './detail/taximeter-offer-item-detail.component';
import { TaximeterOfferItemUpdateComponent } from './update/taximeter-offer-item-update.component';
import { TaximeterOfferItemDeleteDialogComponent } from './delete/taximeter-offer-item-delete-dialog.component';
import { TaximeterOfferItemRoutingModule } from './route/taximeter-offer-item-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterOfferItemRoutingModule],
  declarations: [
    TaximeterOfferItemComponent,
    TaximeterOfferItemDetailComponent,
    TaximeterOfferItemUpdateComponent,
    TaximeterOfferItemDeleteDialogComponent,
  ],
  entryComponents: [TaximeterOfferItemDeleteDialogComponent],
})
export class TaximeterOfferItemModule {}
