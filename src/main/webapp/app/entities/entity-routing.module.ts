import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'taximeter-offer',
        data: { pageTitle: 'taximeterApp.taximeterOffer.home.title' },
        loadChildren: () => import('./taximeter-offer/taximeter-offer.module').then(m => m.TaximeterOfferModule),
      },
      {
        path: 'taximeter-offer-group',
        data: { pageTitle: 'taximeterApp.taximeterOfferGroup.home.title' },
        loadChildren: () => import('./taximeter-offer-group/taximeter-offer-group.module').then(m => m.TaximeterOfferGroupModule),
      },
      {
        path: 'taximeter-offer-item',
        data: { pageTitle: 'taximeterApp.taximeterOfferItem.home.title' },
        loadChildren: () => import('./taximeter-offer-item/taximeter-offer-item.module').then(m => m.TaximeterOfferItemModule),
      },
      {
        path: 'taximeter-fixed-list',
        data: { pageTitle: 'taximeterApp.taximeterFixedList.home.title' },
        loadChildren: () => import('./taximeter-fixed-list/taximeter-fixed-list.module').then(m => m.TaximeterFixedListModule),
      },
      {
        path: 'taximeter-fixed-list-item',
        data: { pageTitle: 'taximeterApp.taximeterFixedListItem.home.title' },
        loadChildren: () =>
          import('./taximeter-fixed-list-item/taximeter-fixed-list-item.module').then(m => m.TaximeterFixedListItemModule),
      },
      {
        path: 'taximeter-formula',
        data: { pageTitle: 'taximeterApp.taximeterFormula.home.title' },
        loadChildren: () => import('./taximeter-formula/taximeter-formula.module').then(m => m.TaximeterFormulaModule),
      },
      {
        path: 'taximeter-time-range',
        data: { pageTitle: 'taximeterApp.taximeterTimeRange.home.title' },
        loadChildren: () => import('./taximeter-time-range/taximeter-time-range.module').then(m => m.TaximeterTimeRangeModule),
      },
      {
        path: 'taximeter-time-range-item',
        data: { pageTitle: 'taximeterApp.taximeterTimeRangeItem.home.title' },
        loadChildren: () =>
          import('./taximeter-time-range-item/taximeter-time-range-item.module').then(m => m.TaximeterTimeRangeItemModule),
      },
      {
        path: 'preference',
        data: { pageTitle: 'taximeterApp.preference.home.title' },
        loadChildren: () => import('./preference/preference.module').then(m => m.PreferenceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
