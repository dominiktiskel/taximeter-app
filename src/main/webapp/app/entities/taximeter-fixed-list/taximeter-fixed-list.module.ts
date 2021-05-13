import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaximeterFixedListComponent } from './list/taximeter-fixed-list.component';
import { TaximeterFixedListDetailComponent } from './detail/taximeter-fixed-list-detail.component';
import { TaximeterFixedListUpdateComponent } from './update/taximeter-fixed-list-update.component';
import { TaximeterFixedListDeleteDialogComponent } from './delete/taximeter-fixed-list-delete-dialog.component';
import { TaximeterFixedListRoutingModule } from './route/taximeter-fixed-list-routing.module';

@NgModule({
  imports: [SharedModule, TaximeterFixedListRoutingModule],
  declarations: [
    TaximeterFixedListComponent,
    TaximeterFixedListDetailComponent,
    TaximeterFixedListUpdateComponent,
    TaximeterFixedListDeleteDialogComponent,
  ],
  entryComponents: [TaximeterFixedListDeleteDialogComponent],
})
export class TaximeterFixedListModule {}
