import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterOfferItemComponent } from '../list/taximeter-offer-item.component';
import { TaximeterOfferItemDetailComponent } from '../detail/taximeter-offer-item-detail.component';
import { TaximeterOfferItemUpdateComponent } from '../update/taximeter-offer-item-update.component';
import { TaximeterOfferItemRoutingResolveService } from './taximeter-offer-item-routing-resolve.service';

const taximeterOfferItemRoute: Routes = [
  {
    path: '',
    component: TaximeterOfferItemComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterOfferItemDetailComponent,
    resolve: {
      taximeterOfferItem: TaximeterOfferItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterOfferItemUpdateComponent,
    resolve: {
      taximeterOfferItem: TaximeterOfferItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterOfferItemUpdateComponent,
    resolve: {
      taximeterOfferItem: TaximeterOfferItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterOfferItemRoute)],
  exports: [RouterModule],
})
export class TaximeterOfferItemRoutingModule {}
