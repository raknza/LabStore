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

];

@NgModule({
  imports: [RouterModule.forChild(routes), SharedModule],
  exports: [RouterModule]
})

export class CustomerRoutingModule { }
