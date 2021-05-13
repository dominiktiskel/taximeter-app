import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterTimeRangeComponent } from '../list/taximeter-time-range.component';
import { TaximeterTimeRangeDetailComponent } from '../detail/taximeter-time-range-detail.component';
import { TaximeterTimeRangeUpdateComponent } from '../update/taximeter-time-range-update.component';
import { TaximeterTimeRangeRoutingResolveService } from './taximeter-time-range-routing-resolve.service';

const taximeterTimeRangeRoute: Routes = [
  {
    path: '',
    component: TaximeterTimeRangeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterTimeRangeDetailComponent,
    resolve: {
      taximeterTimeRange: TaximeterTimeRangeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterTimeRangeUpdateComponent,
    resolve: {
      taximeterTimeRange: TaximeterTimeRangeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterTimeRangeUpdateComponent,
    resolve: {
      taximeterTimeRange: TaximeterTimeRangeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterTimeRangeRoute)],
  exports: [RouterModule],
})
export class TaximeterTimeRangeRoutingModule {}
