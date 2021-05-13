import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterFixedListComponent } from '../list/taximeter-fixed-list.component';
import { TaximeterFixedListDetailComponent } from '../detail/taximeter-fixed-list-detail.component';
import { TaximeterFixedListUpdateComponent } from '../update/taximeter-fixed-list-update.component';
import { TaximeterFixedListRoutingResolveService } from './taximeter-fixed-list-routing-resolve.service';

const taximeterFixedListRoute: Routes = [
  {
    path: '',
    component: TaximeterFixedListComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterFixedListDetailComponent,
    resolve: {
      taximeterFixedList: TaximeterFixedListRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterFixedListUpdateComponent,
    resolve: {
      taximeterFixedList: TaximeterFixedListRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterFixedListUpdateComponent,
    resolve: {
      taximeterFixedList: TaximeterFixedListRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterFixedListRoute)],
  exports: [RouterModule],
})
export class TaximeterFixedListRoutingModule {}
