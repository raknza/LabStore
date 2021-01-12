import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import { DefaultLayoutComponent } from './containers';
import { P404Component } from './views/error/404.component';
import { P500Component } from './views/error/500.component';
import { LoginComponent } from './views/login/login.component';

// import canActive
import { CanActiveCustomerService } from './services/can-active-customer.service';
import { CanActiveBossService } from './services/can-active-boss.service';


export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: '404',
    component: P404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '500',
    component: P500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'boss',
        canActivate: [CanActiveBossService],
        loadChildren: () => import('./views/boss/boss.module').then(m => m.BossModule)
      },
      {
        path: 'customer',
        canActivate: [CanActiveCustomerService],
        loadChildren: () => import('./views/customer/customer.module').then(m => m.CustomerModule)
      }
    ]
  },
  { path: '**', component: P404Component }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [CanActiveCustomerService, CanActiveBossService]
})
export class AppRoutingModule { }
