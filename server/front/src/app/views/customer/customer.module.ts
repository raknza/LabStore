import { CustomerRoutingModule } from './customer-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollapseModule } from 'ngx-bootstrap/collapse';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { FormsModule } from '@angular/forms';
// Pagination Component
import { PaginationModule } from 'ngx-bootstrap/pagination';

// Dropdowns Component
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { SharedModule } from '../shared/shared.module';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    CustomerRoutingModule,
    CollapseModule.forRoot(),
    CarouselModule.forRoot(),
    PaginationModule.forRoot(),
    BsDropdownModule.forRoot(),
    SharedModule,
  ],
})
export class CustomerModule { }
