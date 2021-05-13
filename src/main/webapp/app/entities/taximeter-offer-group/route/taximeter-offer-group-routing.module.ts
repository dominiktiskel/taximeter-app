import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterOfferGroupComponent } from '../list/taximeter-offer-group.component';
import { TaximeterOfferGroupDetailComponent } from '../detail/taximeter-offer-group-detail.component';
import { TaximeterOfferGroupUpdateComponent } from '../update/taximeter-offer-group-update.component';
import { TaximeterOfferGroupRoutingResolveService } from './taximeter-offer-group-routing-resolve.service';

const taximeterOfferGroupRoute: Routes = [
  {
    path: '',
    component: TaximeterOfferGroupComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterOfferGroupDetailComponent,
    resolve: {
      taximeterOfferGroup: TaximeterOfferGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterOfferGroupUpdateComponent,
    resolve: {
      taximeterOfferGroup: TaximeterOfferGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterOfferGroupUpdateComponent,
    resolve: {
      taximeterOfferGroup: TaximeterOfferGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterOfferGroupRoute)],
  exports: [RouterModule],
})
export class TaximeterOfferGroupRoutingModule {}
