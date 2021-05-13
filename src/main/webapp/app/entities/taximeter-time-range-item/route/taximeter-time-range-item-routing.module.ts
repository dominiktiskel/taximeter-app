import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterTimeRangeItemComponent } from '../list/taximeter-time-range-item.component';
import { TaximeterTimeRangeItemDetailComponent } from '../detail/taximeter-time-range-item-detail.component';
import { TaximeterTimeRangeItemUpdateComponent } from '../update/taximeter-time-range-item-update.component';
import { TaximeterTimeRangeItemRoutingResolveService } from './taximeter-time-range-item-routing-resolve.service';

const taximeterTimeRangeItemRoute: Routes = [
  {
    path: '',
    component: TaximeterTimeRangeItemComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterTimeRangeItemDetailComponent,
    resolve: {
      taximeterTimeRangeItem: TaximeterTimeRangeItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterTimeRangeItemUpdateComponent,
    resolve: {
      taximeterTimeRangeItem: TaximeterTimeRangeItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterTimeRangeItemUpdateComponent,
    resolve: {
      taximeterTimeRangeItem: TaximeterTimeRangeItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterTimeRangeItemRoute)],
  exports: [RouterModule],
})
export class TaximeterTimeRangeItemRoutingModule {}
