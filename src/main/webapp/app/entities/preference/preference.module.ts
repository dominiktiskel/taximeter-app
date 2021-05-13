import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { PreferenceComponent } from './list/preference.component';
import { PreferenceDetailComponent } from './detail/preference-detail.component';
import { PreferenceUpdateComponent } from './update/preference-update.component';
import { PreferenceDeleteDialogComponent } from './delete/preference-delete-dialog.component';
import { PreferenceRoutingModule } from './route/preference-routing.module';

@NgModule({
  imports: [SharedModule, PreferenceRoutingModule],
  declarations: [PreferenceComponent, PreferenceDetailComponent, PreferenceUpdateComponent, PreferenceDeleteDialogComponent],
  entryComponents: [PreferenceDeleteDialogComponent],
})
export class PreferenceModule {}
