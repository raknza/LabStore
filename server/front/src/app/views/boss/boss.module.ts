import { ShelfProductManagementComponent } from './shelf-product-management/shelf-product-management.component';
import { ProductManagementComponent } from './product-management/product-management.component';
import { PurchaseDashboardComponent } from './purchase-dashboard/purchase-dashboard.component';
import { TransactionDashboardComponent } from './transaction-dashboard/transaction-dashboard.component';
import { CategoryDashboardComponent } from './category-dashboard/category-dashboard.component';
import { BossRoutingModule } from './boss-routing.module';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';

// Pagination Component
import { PaginationModule } from 'ngx-bootstrap/pagination';

// Dropdowns Component
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

//  Modal Component
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BossRoutingModule,
    CollapseModule.forRoot(),
    CarouselModule.forRoot(),
    PaginationModule.forRoot(),
    BsDropdownModule.forRoot(),
    SharedModule,
    ModalModule.forRoot(),
  ],
  declarations: [
    TransactionDashboardComponent, PurchaseDashboardComponent, CategoryDashboardComponent , ProductManagementComponent,
    ShelfProductManagementComponent
  ]
})
export class BossModule { }
