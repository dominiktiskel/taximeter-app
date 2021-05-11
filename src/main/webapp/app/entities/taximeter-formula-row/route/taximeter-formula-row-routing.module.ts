import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterFormulaRowComponent } from '../list/taximeter-formula-row.component';
import { TaximeterFormulaRowDetailComponent } from '../detail/taximeter-formula-row-detail.component';
import { TaximeterFormulaRowUpdateComponent } from '../update/taximeter-formula-row-update.component';
import { TaximeterFormulaRowRoutingResolveService } from './taximeter-formula-row-routing-resolve.service';

const taximeterFormulaRowRoute: Routes = [
  {
    path: '',
    component: TaximeterFormulaRowComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterFormulaRowDetailComponent,
    resolve: {
      taximeterFormulaRow: TaximeterFormulaRowRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterFormulaRowUpdateComponent,
    resolve: {
      taximeterFormulaRow: TaximeterFormulaRowRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterFormulaRowUpdateComponent,
    resolve: {
      taximeterFormulaRow: TaximeterFormulaRowRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterFormulaRowRoute)],
  exports: [RouterModule],
})
export class TaximeterFormulaRowRoutingModule {}
