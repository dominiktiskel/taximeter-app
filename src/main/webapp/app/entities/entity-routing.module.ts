import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'taximeter-formula',
        data: { pageTitle: 'taximeterApp.taximeterFormula.home.title' },
        loadChildren: () => import('./taximeter-formula/taximeter-formula.module').then(m => m.TaximeterFormulaModule),
      },
      {
        path: 'taximeter-formula-row',
        data: { pageTitle: 'taximeterApp.taximeterFormulaRow.home.title' },
        loadChildren: () => import('./taximeter-formula-row/taximeter-formula-row.module').then(m => m.TaximeterFormulaRowModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
