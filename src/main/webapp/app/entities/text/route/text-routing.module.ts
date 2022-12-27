import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TextComponent } from '../list/text.component';
import { TextDetailComponent } from '../detail/text-detail.component';
import { TextUpdateComponent } from '../update/text-update.component';
import { TextRoutingResolveService } from './text-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const textRoute: Routes = [
  {
    path: '',
    component: TextComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TextDetailComponent,
    resolve: {
      text: TextRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TextUpdateComponent,
    resolve: {
      text: TextRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TextUpdateComponent,
    resolve: {
      text: TextRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(textRoute)],
  exports: [RouterModule],
})
export class TextRoutingModule {}
