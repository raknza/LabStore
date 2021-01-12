import { ProductManagementComponent } from './product-management/product-management.component';
import { CategoryDashboardComponent } from './category-dashboard/category-dashboard.component';
import { PurchaseDashboardComponent } from './purchase-dashboard/purchase-dashboard.component';
import { TransactionDashboardComponent } from './transaction-dashboard/transaction-dashboard.component';
import { ProductDetailComponent } from './../shared/product-detail/product-detail.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';


const routes: Routes = [
  {
    path: '',
    component: ProductDetailComponent,
    data: {
      title: 'Product Detail'
    }
  },
  {
    path: 'transaction-dashboard',
    component: TransactionDashboardComponent,
    data: {
      title: 'Transaction Dashboard'
    }
  },
  {
    path: 'purchase-dashboard',
    component: PurchaseDashboardComponent,
    data: {
      title: 'Purchase Dashboard'
    }
  },
  {
    path: 'category-dashboard',
    component: CategoryDashboardComponent,
    data: {
      title: 'Category Dashboard'
    }
  },
  {
    path: 'product-management',
    component: ProductManagementComponent,
    data: {
      title: 'Product Management'
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes), SharedModule],
  exports: [RouterModule]
})

export class BossRoutingModule { }
