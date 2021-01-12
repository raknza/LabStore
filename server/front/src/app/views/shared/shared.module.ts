import { ProductDetailComponent } from './product-detail/product-detail.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollapseModule } from 'ngx-bootstrap/collapse';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { FormsModule } from '@angular/forms';
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
    CollapseModule.forRoot(),
    CarouselModule.forRoot(),
    PaginationModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
  ],
  declarations: [
    ProductDetailComponent,
  ],
  exports: [
    ProductDetailComponent,
  ]
})
export class SharedModule { }
