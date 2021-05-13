import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterOfferGroupComponent } from './list/taximeter-offer-group.component';
import { TaximeterOfferGroupDetailComponent } from './detail/taximeter-offer-group-detail.component';
import { TaximeterOfferGroupUpdateComponent } from './update/taximeter-offer-group-update.component';
import { TaximeterOfferGroupDeleteDialogComponent } from './delete/taximeter-offer-group-delete-dialog.component';
import { TaximeterOfferGroupRoutingModule } from './route/taximeter-offer-group-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterOfferGroupRoutingModule],
  declarations: [
    TaximeterOfferGroupComponent,
    TaximeterOfferGroupDetailComponent,
    TaximeterOfferGroupUpdateComponent,
    TaximeterOfferGroupDeleteDialogComponent,
  ],
  entryComponents: [TaximeterOfferGroupDeleteDialogComponent],
})
export class TaximeterOfferGroupModule {}
