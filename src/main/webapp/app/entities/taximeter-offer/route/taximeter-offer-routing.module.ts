import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterOfferComponent } from '../list/taximeter-offer.component';
import { TaximeterOfferDetailComponent } from '../detail/taximeter-offer-detail.component';
import { TaximeterOfferUpdateComponent } from '../update/taximeter-offer-update.component';
import { TaximeterOfferRoutingResolveService } from './taximeter-offer-routing-resolve.service';

const taximeterOfferRoute: Routes = [
  {
    path: '',
    component: TaximeterOfferComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterOfferDetailComponent,
    resolve: {
      taximeterOffer: TaximeterOfferRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterOfferUpdateComponent,
    resolve: {
      taximeterOffer: TaximeterOfferRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterOfferUpdateComponent,
    resolve: {
      taximeterOffer: TaximeterOfferRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterOfferRoute)],
  exports: [RouterModule],
})
export class TaximeterOfferRoutingModule {}
