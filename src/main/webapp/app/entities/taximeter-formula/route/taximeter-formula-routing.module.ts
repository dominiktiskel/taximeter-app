import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaximeterFormulaComponent } from '../list/taximeter-formula.component';
import { TaximeterFormulaDetailComponent } from '../detail/taximeter-formula-detail.component';
import { TaximeterFormulaUpdateComponent } from '../update/taximeter-formula-update.component';
import { TaximeterFormulaRoutingResolveService } from './taximeter-formula-routing-resolve.service';

const taximeterFormulaRoute: Routes = [
  {
    path: '',
    component: TaximeterFormulaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaximeterFormulaDetailComponent,
    resolve: {
      taximeterFormula: TaximeterFormulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaximeterFormulaUpdateComponent,
    resolve: {
      taximeterFormula: TaximeterFormulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaximeterFormulaUpdateComponent,
    resolve: {
      taximeterFormula: TaximeterFormulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taximeterFormulaRoute)],
  exports: [RouterModule],
})
export class TaximeterFormulaRoutingModule {}
