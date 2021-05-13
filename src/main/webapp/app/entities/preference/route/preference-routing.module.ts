import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PreferenceComponent } from '../list/preference.component';
import { PreferenceDetailComponent } from '../detail/preference-detail.component';
import { PreferenceUpdateComponent } from '../update/preference-update.component';
import { PreferenceRoutingResolveService } from './preference-routing-resolve.service';

const preferenceRoute: Routes = [
  {
    path: '',
    component: PreferenceComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PreferenceDetailComponent,
    resolve: {
      preference: PreferenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PreferenceUpdateComponent,
    resolve: {
      preference: PreferenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PreferenceUpdateComponent,
    resolve: {
      preference: PreferenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(preferenceRoute)],
  exports: [RouterModule],
})
export class PreferenceRoutingModule {}
