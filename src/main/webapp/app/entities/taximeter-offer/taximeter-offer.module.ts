import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterOfferComponent } from './list/taximeter-offer.component';
import { TaximeterOfferDetailComponent } from './detail/taximeter-offer-detail.component';
import { TaximeterOfferUpdateComponent } from './update/taximeter-offer-update.component';
import { TaximeterOfferDeleteDialogComponent } from './delete/taximeter-offer-delete-dialog.component';
import { TaximeterOfferRoutingModule } from './route/taximeter-offer-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterOfferRoutingModule],
  declarations: [
    TaximeterOfferComponent,
    TaximeterOfferDetailComponent,
    TaximeterOfferUpdateComponent,
    TaximeterOfferDeleteDialogComponent,
  ],
  entryComponents: [TaximeterOfferDeleteDialogComponent],
})
export class TaximeterOfferModule {}
