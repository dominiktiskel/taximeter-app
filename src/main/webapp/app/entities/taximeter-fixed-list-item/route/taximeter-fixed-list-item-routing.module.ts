import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterFixedListItemComponent } from '../list/taximeter-fixed-list-item.component';
import { TaximeterFixedListItemDetailComponent } from '../detail/taximeter-fixed-list-item-detail.component';
import { TaximeterFixedListItemUpdateComponent } from '../update/taximeter-fixed-list-item-update.component';
import { TaximeterFixedListItemRoutingResolveService } from './taximeter-fixed-list-item-routing-resolve.service';

const taximeterFixedListItemRoute: Routes = [
  {
    path: '',
    component: TaximeterFixedListItemComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterFixedListItemDetailComponent,
    resolve: {
      taximeterFixedListItem: TaximeterFixedListItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterFixedListItemUpdateComponent,
    resolve: {
      taximeterFixedListItem: TaximeterFixedListItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterFixedListItemUpdateComponent,
    resolve: {
      taximeterFixedListItem: TaximeterFixedListItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterFixedListItemRoute)],
  exports: [RouterModule],
})
export class TaximeterFixedListItemRoutingModule {}
